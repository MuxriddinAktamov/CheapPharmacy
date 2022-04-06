package com.company.service;

import com.company.dto.*;
import com.company.entity.CommentEntity;
import com.company.entity.MedicineEntity;
import com.company.entity.PharmacyEntity;
import com.company.entity.PharmacyMedicineEntity;
import com.company.repository.PharmacyMedicineRepository;
import com.company.spec.CommentSpecfication;
import com.company.spec.PharmacyMedicineSpecfication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PharmacyMedicineServise {
    @Autowired
    private PharmacyMedicineRepository pharmacyMedicineRepository;
    @Autowired
    private MedicineServise medicineServise;
    @Autowired
    private PharmacyServise pharmacyServise;


    public PharmacyMedicineDTO create(PharmacyMedicineDTO dto) {
        MedicineEntity medicine = medicineServise.get(dto.getMediciId());
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getPharmacyId());

        PharmacyMedicineEntity pharmacyMedicine = new PharmacyMedicineEntity();
        pharmacyMedicine.setMedicine(medicine);
        pharmacyMedicine.setApteka(pharmacy);
        pharmacyMedicine.setCreated_date(LocalDateTime.now());
        pharmacyMedicineRepository.save(pharmacyMedicine);
        dto.setId(pharmacyMedicine.getId());
        return dto;
    }

    public PharmacyMedicineDTO update(PharmacyMedicineDTO dto, Integer id) {
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getPharmacyId());
        MedicineEntity medicine = medicineServise.get(dto.getMediciId());
        PharmacyMedicineEntity entity = get(id);
        entity.setMedicine(medicine);
        entity.setApteka(pharmacy);
        pharmacyMedicineRepository.save(entity);
        return toDTO(entity);

    }

    public PharmacyMedicineEntity get(Integer id) {
        return pharmacyMedicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Not found"));
    }

    public PharmacyMedicineDTO toDTO(PharmacyMedicineEntity entity) {
        PharmacyMedicineDTO dto = new PharmacyMedicineDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreated_date());
        dto.setPharmacyId(entity.getApteka().getId());
        dto.setMediciId(entity.getMedicine().getId());
        return dto;
    }

    public PharmacyMedicineDTO getById(Integer id) {
        PharmacyMedicineEntity entity = pharmacyMedicineRepository.findById(id).orElseThrow(() -> new RuntimeException("PharmacyMedicine Not Found"));
        return toDTO(entity);
    }

    public PharmacyMedicineDTO delete(Integer id) {
        PharmacyMedicineEntity entity = get(id);
        pharmacyMedicineRepository.delete(entity);
        return toDTO(entity);
    }

    public List<PharmacyMedicineDTO> getByMedicineId(Integer id) {
        List<PharmacyMedicineDTO> list = new LinkedList<>();
        List<PharmacyMedicineEntity> entities = pharmacyMedicineRepository.findByMedicineId(id);
        Iterator<PharmacyMedicineEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public List<PharmacyMedicineDTO> getByPharmacyId(Integer id) {
        List<PharmacyMedicineDTO> list = new LinkedList<>();
        List<PharmacyMedicineEntity> entities = pharmacyMedicineRepository.findByAptekaId(id);
        Iterator<PharmacyMedicineEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public PageImpl<PharmacyMedicineDTO> filterSpe(int page, int size, PharmacyMedicineFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<PharmacyMedicineEntity> spec = Specification.where(PharmacyMedicineSpecfication.isNotNull("id"));
        if (filterDTO.getPharmacyId() != null) {
            spec.and(PharmacyMedicineSpecfication.id("pharmacyId", filterDTO.getPharmacyId()));
        }
        if (filterDTO.getMediciId() != null) {
            spec.and(PharmacyMedicineSpecfication.id("mediciId", filterDTO.getMediciId()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(PharmacyMedicineSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<PharmacyMedicineEntity> pharmacyMedicineEntityPage = pharmacyMedicineRepository.findAll(spec, pageable);
        List<PharmacyMedicineDTO> dtoList = pharmacyMedicineEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(pharmacyMedicineEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, pharmacyMedicineEntityPage.getTotalElements());
    }
}
