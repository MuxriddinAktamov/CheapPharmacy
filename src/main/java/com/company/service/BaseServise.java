package com.company.service;

import com.company.dto.BaseDTO;
import com.company.dto.BaseFilterDTO;
import com.company.dto.CommentDTO;
import com.company.entity.*;
import com.company.repository.BaseRepository;
import com.company.spec.BaseSpecfication;
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
public class BaseServise {

    @Autowired
    private BaseRepository baseRepository;
    @Autowired
    private ProfileServise profileServise;
    @Autowired
    private MedicineServise medicineServise;
    @Autowired
    private PharmacyServise pharmacyServise;

    public BaseDTO create(BaseDTO dto) {
        ProfileEntity profileEntity = profileServise.get(dto.getProfileId());
        MedicineEntity medicine = medicineServise.get(dto.getMedicineId());
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getPharmacyid());

        BaseEntity entity = new BaseEntity();
        entity.setProfile(profileEntity);
        entity.setPharmacy(pharmacy);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setMedicine(medicine);
        baseRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public BaseEntity get(Integer id) {
        return baseRepository.findById(id).orElseThrow(() -> new RuntimeException("Base Not found"));
    }

    public BaseDTO update(BaseDTO dto, Integer id) {
        ProfileEntity profileEntity = profileServise.get(dto.getProfileId());
        MedicineEntity medicine = medicineServise.get(dto.getMedicineId());
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getPharmacyid());


        BaseEntity entity = get(id);
        entity.setPharmacy(pharmacy);
        entity.setProfile(profileEntity);
        entity.setMedicine(medicine);

        baseRepository.save(entity);
        return dto;
    }

    public BaseDTO getById(Integer id) {
        BaseEntity entity = baseRepository.findById(id).orElseThrow(() -> new RuntimeException("Base Not found"));
        return toDTO(entity);
    }

    public List<BaseDTO> AllApteka() {
        List<BaseDTO> list = new LinkedList<>();
        Iterable<BaseEntity> entities = baseRepository.findAll();
        Iterator<BaseEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public BaseDTO toDTO(BaseEntity entity) {
        BaseDTO dto = new BaseDTO();
        dto.setId(entity.getId());
        dto.setPharmacyid(entity.getPharmacy().getId());
        dto.setUpdateDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfile().getId());
        dto.setCreated_date(entity.getCreatedDate());
        dto.setMedicineId(entity.getMedicine().getId());
        return dto;
    }

    public List<BaseDTO> getBaseByMedicineId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<BaseDTO> list = new LinkedList<>();
        Iterable<BaseEntity> baseEntities = baseRepository.findByMedicineId(id, pageable);
        Iterator<BaseEntity> entityIterator = baseEntities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;

    }

    public List<BaseDTO> getBaseByProfileId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<BaseDTO> list = new LinkedList<>();
        Iterable<BaseEntity> baseEntities = baseRepository.findByProfileId(id, pageable);
        Iterator<BaseEntity> entityIterator = baseEntities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;

    }

    public List<BaseDTO> getBaseByPharmacyId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<BaseDTO> list = new LinkedList<>();
        Iterable<BaseEntity> baseEntities = baseRepository.findByPharmacyId(id, pageable);
        Iterator<BaseEntity> entityIterator = baseEntities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;

    }

    public void delete(Integer id) {
        BaseEntity entity = get(id);
        baseRepository.delete(entity);
    }

    public void deleteByMedicineId(Integer id) {
        List<BaseEntity> entity = baseRepository.findByMedicineId(id);
        baseRepository.deleteAll(entity);
    }

    public void deleteByProfileId(Integer id) {
        List<BaseEntity> entity = baseRepository.findByProfileId(id);
        baseRepository.deleteAll(entity);
    }

    public void deleteByPharmacyId(Integer id) {
        List<BaseEntity> entity = baseRepository.findByProfileId(id);
        baseRepository.deleteAll(entity);
    }

    public PageImpl<BaseDTO> filterSpe(int page, int size, BaseFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<BaseEntity> spec = Specification.where(BaseSpecfication.isNotNull("id"));
        if (filterDTO.getPharmacyid() != null) {
            spec.and(BaseSpecfication.id("content", filterDTO.getPharmacyid()));
        }
        if (filterDTO.getMedicineId() != null) {
            spec.and(BaseSpecfication.id("articleId", filterDTO.getMedicineId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(BaseSpecfication.id("profileId", filterDTO.getProfileId()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(BaseSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<BaseEntity> baseEntityPage = baseRepository.findAll(spec, pageable);
        List<BaseDTO> dtoList = baseEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(baseEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, baseEntityPage.getTotalElements());
    }
}