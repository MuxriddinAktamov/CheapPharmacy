package com.company.service;

import com.company.dto.CompanyDTO;
import com.company.dto.CompanyFilterDTO;
import com.company.dto.MedicineDTO;
import com.company.dto.MedicineFilterDTO;
import com.company.entity.PharmacyEntity;
import com.company.entity.CompanyEntity;
import com.company.entity.MedicineEntity;
import com.company.exceptions.BadRequestException;
import com.company.repository.MedicineRepository;
import com.company.spec.CommentSpecfication;
import com.company.spec.CompanySpecfication;
import com.company.spec.MedicineSpecfication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServise {
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private CompanyServise companyServise;
    @Autowired
    private AttachServise attachServise;

    public MedicineDTO create(MedicineDTO medicineDTO) {
        CompanyEntity company = companyServise.get(medicineDTO.getCompanyId());
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineKategory(medicineDTO.getMedicineKategory());
        medicine.setAmount(medicineDTO.getAmount());
        medicine.setCode(medicineDTO.getCode());
        medicine.setCompany(company);
        medicine.setDescription(medicineDTO.getDescription());
        medicine.setCreatedDate(LocalDateTime.now());
        medicine.setExpiration_date(LocalDate.of(2022, Month.MARCH, 14));
        medicine.setName(medicineDTO.getName());
        medicine.setManufacture_date(LocalDate.of(2002, Month.AUGUST, 28));
        medicine.setPrice(medicineDTO.getPrice());
        medicineRepository.save(medicine);
        medicineDTO.setId(medicine.getId());
        return medicineDTO;
    }

    public MedicineEntity get(Integer id) {
        return medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine is Not Found"));
    }

    public MedicineDTO update(MedicineDTO medicineDTO, Integer id) {
        CompanyEntity company = companyServise.get(medicineDTO.getCompanyId());

        MedicineEntity medicine = get(id);
        medicine.setMedicineKategory(medicineDTO.getMedicineKategory());
        medicine.setAmount(medicineDTO.getAmount());
        medicine.setCode(medicineDTO.getCode());
        medicine.setCompany(company);
        medicine.setDescription(medicineDTO.getDescription());
        medicine.setExpiration_date(LocalDate.now());
        medicine.setName(medicineDTO.getName());
        medicine.setManufacture_date(LocalDate.now());
        medicine.setPrice(medicineDTO.getPrice());
        medicineRepository.save(medicine);
        return medicineDTO;
    }

    public MedicineDTO getById(Integer id) {
        MedicineEntity entity = medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine is Not Found"));
        return toDTO(entity);

    }

    public MedicineDTO toDTO(MedicineEntity entity) {
        MedicineDTO dto = new MedicineDTO();
        dto.setMedicineKategory(entity.getMedicineKategory());
        dto.setAmount(entity.getAmount());
        dto.setCode(entity.getCode());
        dto.setCompanyId(entity.getCompany().getId());
        dto.setDescription(entity.getDescription());
        dto.setExpiration_date(entity.getExpiration_date());
        dto.setName(entity.getName());
        dto.setManufacture_date(entity.getManufacture_date());
        dto.setPrice(entity.getPrice());
        dto.setCreated_date(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }

    public void delete(Integer id) {
        MedicineEntity entity = get(id);
        medicineRepository.delete(entity);
    }

    public List<MedicineDTO> AllMedicine() {
        List<MedicineDTO> list = new LinkedList<>();
        Iterable<MedicineEntity> entities = medicineRepository.findAll();
        Iterator<MedicineEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<MedicineDTO> getByMedicineName(String name, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);

        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Name is Null or Is Empty");
        }

        List<MedicineDTO> medicineDTOS = new LinkedList<>();
        List<MedicineEntity> entities = medicineRepository.findByName(name, pageable);
        Iterator<MedicineEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            medicineDTOS.add(toDTO(iterator.next()));
        }
        return medicineDTOS;

    }

    public List<MedicineDTO> getByCompanyId(Integer id, Integer size, Integer page) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        Pageable pageable = PageRequest.of(size, page);
        List<MedicineDTO> list = new LinkedList<>();
        Iterable<MedicineEntity> entities = medicineRepository.findByCompanyId(id, pageable);
        Iterator<MedicineEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;

    }

    public PageImpl<MedicineDTO> filterSpe(int page, int size, MedicineFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<MedicineEntity> spec = Specification.where(MedicineSpecfication.isNotNull("id"));
        if (filterDTO.getDescription() != null || !filterDTO.getDescription().isEmpty()) {
            spec.and(MedicineSpecfication.equal("description", filterDTO.getDescription()));
        }
        if (filterDTO.getCode() != null || !filterDTO.getCode().isEmpty()) {
            spec.and(MedicineSpecfication.equal("code", filterDTO.getCode()));
        }
        if (filterDTO.getName() != null || !filterDTO.getName().isEmpty()) {
            spec.and(MedicineSpecfication.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getAmount() != null) {
            spec.and(MedicineSpecfication.id("amount", filterDTO.getAmount()));
        }
        if (filterDTO.getCompanyId() != null) {
            spec.and(MedicineSpecfication.id("companyId", filterDTO.getCompanyId()));
        }
        if (filterDTO.getPrice() != null) {
            spec.and(MedicineSpecfication.price("price", filterDTO.getPrice()));
        }
        if (filterDTO.getMedicineKategory() != null) {
            spec.and(MedicineSpecfication.kategory("medicineKategory", filterDTO.getMedicineKategory()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(MedicineSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<MedicineEntity> medicineEntityPage = medicineRepository.findAll(spec, pageable);
        List<MedicineDTO> dtoList = medicineEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(medicineEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, medicineEntityPage.getTotalElements());
    }

}
