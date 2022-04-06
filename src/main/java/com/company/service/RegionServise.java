package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.dto.RegionDTO;
import com.company.dto.RegionFilterDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.exceptions.BadRequestException;
import com.company.repository.RegionRepository;
import com.company.spec.ProfileSpecification;
import com.company.spec.RegionSpecfication;
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
public class RegionServise {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setRegion(dto.getRegion());
        entity.setDistrict(dto.getDistrict());
        entity.setCreatedDate(LocalDateTime.now());

        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region Not Found"));
    }

    public RegionDTO update(RegionDTO dto, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }

        RegionEntity entity = get(id);
        entity.setRegion(dto.getRegion());
        entity.setDistrict(dto.getDistrict());

        regionRepository.save(entity);
        return dto;
    }

    public RegionDTO getById(Integer id) {
        RegionEntity entity = regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region Not Found"));
        return toDTO(entity);
    }

    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setDistrict(entity.getDistrict());
        dto.setRegion(entity.getRegion());
        dto.setCreated_date(entity.getCreatedDate());
        return dto;
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        RegionEntity entity = get(id);
        regionRepository.delete(entity);
    }

    public List<RegionDTO> AllRegion() {
        List<RegionDTO> list = new LinkedList<>();
        Iterable<RegionEntity> entities = regionRepository.findAll();
        Iterator<RegionEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public PageImpl<RegionDTO> filterSpe(int page, int size, RegionFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<RegionEntity> spec = Specification.where(RegionSpecfication.isNotNull("id"));
        if (filterDTO.getDistrict() != null || !filterDTO.getDistrict().isEmpty()) {
            spec.and(RegionSpecfication.equal("district", filterDTO.getDistrict()));
        }
        if (filterDTO.getParentId() != null) {
            spec.and(RegionSpecfication.id("parentId", filterDTO.getParentId()));
        }
        if (filterDTO.getRegion() != null) {
            spec.and(RegionSpecfication.status(filterDTO.getRegion()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(RegionSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<RegionEntity> regionEntityPage = regionRepository.findAll(spec, pageable);
        List<RegionDTO> dtoList = regionEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(regionEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, regionEntityPage.getTotalElements());
    }

}
