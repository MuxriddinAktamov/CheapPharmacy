package com.company.service;

import com.company.dto.PharmacyDTO;
import com.company.dto.PharmacyFilterDTO;
import com.company.dto.PharmacyMedicineDTO;
import com.company.dto.PharmacyMedicineFilterDTO;
import com.company.entity.PharmacyEntity;
import com.company.entity.PharmacyMedicineEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.exceptions.BadRequestException;
import com.company.repository.PharmacyRepository;
import com.company.spec.PharmacyMedicineSpecfication;
import com.company.spec.PharmacySpecfication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PharmacyServise {
    @Autowired
    private PharmacyRepository aptekaRepository;
    @Autowired
    private RegionServise regionServise;

    public PharmacyDTO create(PharmacyDTO dto) {
//        status check
        RegionEntity region = regionServise.get(dto.getRegionId());

        PharmacyEntity entity = new PharmacyEntity();

        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setAdress(dto.getAdress());
        entity.setEmail(dto.getEmail());
        entity.setRegion(region);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        aptekaRepository.save(entity);
        dto.setId(entity.getId());
        return dto;

    }

    public PharmacyEntity get(Integer id) {
        return aptekaRepository.findById(id).orElseThrow(() -> new RuntimeException("Apteka Not Found"));
    }

    public PharmacyDTO update(PharmacyDTO dto, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        PharmacyEntity apteka = get(id);

        RegionEntity region = regionServise.get(dto.getRegionId());

        apteka.setName(dto.getName());
        apteka.setPhone(dto.getPhone());
        apteka.setAdress(dto.getAdress());
        apteka.setEmail(dto.getEmail());
        apteka.setRegion(region);
        apteka.setStartDate(dto.getStartDate());
        apteka.setEndDate(dto.getEndDate());
        aptekaRepository.save(apteka);
        return dto;
    }

    public PharmacyDTO getById(Integer id) {
        PharmacyEntity entity = aptekaRepository.findById(id).orElseThrow(() -> new RuntimeException("Apteka Not Found"));
        return toDTO(entity);
    }

    public void delete(Integer id) {
        PharmacyEntity entity = get(id);
        aptekaRepository.delete(entity);
    }

    public List<PharmacyDTO> AllApteka() {
        List<PharmacyDTO> list = new LinkedList<>();
        Iterable<PharmacyEntity> entities = aptekaRepository.findAll();
        Iterator<PharmacyEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public PharmacyDTO toDTO(PharmacyEntity entity) {
        PharmacyDTO dto = new PharmacyDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setAdress(entity.getAdress());
        dto.setEmail(entity.getEmail());
        dto.setRegionId(entity.getRegion().getId());
        dto.setStartDate(entity.getStartDate());
        entity.setCreatedDate(entity.getCreatedDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public PharmacyDTO getPharmacyByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new BadRequestException("Id is Null");
        }
        Optional<PharmacyEntity> entity = aptekaRepository.findByPhone(phone);
        PharmacyDTO dto = toDTO(entity.get());
        return dto;
    }

    public PharmacyDTO getPharmacyByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Id is Null");
        }
        Optional<PharmacyEntity> entity = aptekaRepository.findByEmail(email);
        PharmacyDTO dto = toDTO(entity.get());
        return dto;
    }

    public PageImpl<PharmacyDTO> filterSpe(int page, int size, PharmacyFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<PharmacyEntity> spec = Specification.where(PharmacySpecfication.isNotNull("id"));
        if (filterDTO.getAdress() != null || !filterDTO.getAdress().isEmpty()) {
            spec.and(PharmacySpecfication.equal("adress", filterDTO.getAdress()));
        }
        if (filterDTO.getEmail() != null || !filterDTO.getEmail().isEmpty()) {
            spec.and(PharmacySpecfication.equal("email", filterDTO.getEmail()));
        }
        if (filterDTO.getPhone() != null || !filterDTO.getPhone().isEmpty()) {
            spec.and(PharmacySpecfication.equal("phone", filterDTO.getPhone()));
        }
        if (filterDTO.getName() != null || !filterDTO.getName().isEmpty()) {
            spec.and(PharmacySpecfication.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getRegionId() != null) {
            spec.and(PharmacySpecfication.id("regionId", filterDTO.getRegionId()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(PharmacySpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<PharmacyEntity> pharmacyEntityPage = aptekaRepository.findAll(spec, pageable);
        List<PharmacyDTO> dtoList = pharmacyEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(pharmacyEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, pharmacyEntityPage.getTotalElements());
    }
}
