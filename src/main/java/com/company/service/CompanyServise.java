package com.company.service;

import com.company.dto.CommentDTO;
import com.company.dto.CommentFilterDTO;
import com.company.dto.CompanyDTO;
import com.company.dto.CompanyFilterDTO;
import com.company.entity.CommentEntity;
import com.company.entity.CompanyEntity;
import com.company.exceptions.BadRequestException;
import com.company.repository.CompanyRepository;
import com.company.spec.CommentSpecfication;
import com.company.spec.CompanySpecfication;
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
public class CompanyServise {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProfileServise profileServise;

    public CompanyDTO create(CompanyDTO dto) {
        CompanyEntity company = new CompanyEntity();
        company.setName(dto.getName());
        company.setAdress(dto.getAdress());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        company.setRegion(dto.getRegion());
        company.setCreatedDate(LocalDateTime.now());
        companyRepository.save(company);
        dto.setId(company.getId());
        return dto;
    }

    public CompanyEntity get(Integer id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));
    }

    public CompanyDTO update(CompanyDTO dto, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        CompanyEntity company = get(id);

        company.setName(dto.getName());
        company.setAdress(dto.getAdress());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        company.setRegion(dto.getRegion());
        companyRepository.save(company);
        return dto;
    }

    public CompanyDTO getById(Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        CompanyEntity entity = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company Not found"));
        return toDTO(entity);
    }

    public CompanyDTO toDTO(CompanyEntity entity) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setAdress(entity.getAdress());
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setRegion(entity.getRegion());
        dto.setCreated_date(entity.getCreatedDate());
        return dto;
    }

    public void delete(Integer id) {
        CompanyEntity entity = get(id);
        companyRepository.delete(entity);
    }

    public List<CompanyDTO> AllCompany() {
        List<CompanyDTO> list = new LinkedList<>();
        Iterable<CompanyEntity> entities = companyRepository.findAll();
        Iterator<CompanyEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public CompanyDTO getByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new BadRequestException("Email is Null or Empty");
        }
        Optional<CompanyEntity> entity = companyRepository.findByPhone(phone);
        CompanyDTO dto = toDTO(entity.get());
        return dto;
    }

    public CompanyDTO getByEmail(String Email) {
        if (Email == null || Email.isEmpty()) {
            throw new BadRequestException("Email is Null or Empty");
        }
        Optional<CompanyEntity> entity = companyRepository.findByEmail(Email);
        CompanyDTO dto = toDTO(entity.get());
        return dto;
    }

    public PageImpl<CompanyDTO> filterSpe(int page, int size, CompanyFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<CompanyEntity> spec = Specification.where(CompanySpecfication.isNotNull("id"));
        if (filterDTO.getAdress() != null || !filterDTO.getAdress().isEmpty()) {
            spec.and(CompanySpecfication.equal("adress", filterDTO.getAdress()));
        }
        if (filterDTO.getEmail() != null || !filterDTO.getEmail().isEmpty()) {
            spec.and(CompanySpecfication.equal("email", filterDTO.getEmail()));
        }
        if (filterDTO.getName() != null || !filterDTO.getName().isEmpty()) {
            spec.and(CompanySpecfication.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getPhone() != null || !filterDTO.getPhone().isEmpty()) {
            spec.and(CompanySpecfication.equal("phone", filterDTO.getPhone()));
        }
        if (filterDTO.getRegion() != null || !filterDTO.getRegion().isEmpty()) {
            spec.and(CompanySpecfication.equal("region", filterDTO.getRegion()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(CompanySpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<CompanyEntity> companyEntityPage = companyRepository.findAll(spec, pageable);
        List<CompanyDTO> dtoList = companyEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(companyEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, companyEntityPage.getTotalElements());
    }

}
