package com.company.service;

import com.company.dto.BaseDTO;
import com.company.dto.CommentDTO;
import com.company.dto.CommentFilterDTO;
import com.company.entity.*;
import com.company.repository.CommentRepository;
import com.company.spec.CommentSpecfication;
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
public class CommentServise {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileServise profileServise;
    @Autowired
    private MedicineServise medicineServise;
    @Autowired
    private PharmacyServise aptekaServise;

    public CommentDTO create(CommentDTO dto) {

        ProfileEntity entity = profileServise.get(dto.getProfileId());
        PharmacyEntity apteka = aptekaServise.get(dto.getAptekaId());
        MedicineEntity medicine = medicineServise.get(dto.getMedicineId());

        CommentEntity comment = new CommentEntity();
        comment.setContent(dto.getContent());
        comment.setProfile(entity);
        comment.setApteka(apteka);
        comment.setMedicine(medicine);
        comment.setCreated_date(LocalDateTime.now());
        commentRepository.save(comment);
        dto.setId(comment.getId());
        return dto;
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public CommentDTO update(CommentDTO dto, Integer id) {
        ProfileEntity entity = profileServise.get(dto.getProfileId());
        PharmacyEntity apteka = aptekaServise.get(dto.getAptekaId());
        MedicineEntity medicine = medicineServise.get(dto.getMedicineId());

        CommentEntity comment = get(id);

        comment.setContent(dto.getContent());
        comment.setProfile(entity);
        comment.setApteka(apteka);
        comment.setMedicine(medicine);
        comment.setCreated_date(LocalDateTime.now());
        commentRepository.save(comment);
        return dto;

    }

    public CommentDTO getById(Integer id) {
        CommentEntity entity = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        return toDTO(entity);
    }

    public void delete(Integer id) {
        CommentEntity entity = get(id);
        commentRepository.delete(entity);
    }

    public List<CommentDTO> AllApteka() {
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findAll();
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfile().getId());
        dto.setContent(entity.getContent());
        dto.setAptekaId(entity.getApteka().getId());
        dto.setCreated_date(entity.getCreated_date());
        dto.setMedicineId(entity.getMedicine().getId());
        return dto;
    }

    public List<CommentDTO> getByMedicineId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findByMedicineId(id, pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<CommentDTO> getByProfileId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findByProfileId(id, pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<CommentDTO> getByPharmacyId(Integer id, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findByAptekaId(id, pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public PageImpl<CommentDTO> filterSpe(int page, int size, CommentFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<CommentEntity> spec = Specification.where(CommentSpecfication.isNotNull("id"));
        if (filterDTO.getContent() != null || !filterDTO.getContent().isEmpty()) {
            spec.and(CommentSpecfication.equal("content", filterDTO.getContent()));
        }
        if (filterDTO.getAptekaId() != null) {
            spec.and(CommentSpecfication.id("aptekaId", filterDTO.getAptekaId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(CommentSpecfication.id("profileId", filterDTO.getProfileId()));
        }
        if (filterDTO.getMedicineId() != null) {
            spec.and(CommentSpecfication.id("medicineId", filterDTO.getMedicineId()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(CommentSpecfication.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<CommentEntity> commentEntityPage = commentRepository.findAll(spec, pageable);
        List<CommentDTO> dtoList = commentEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(commentEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, commentEntityPage.getTotalElements());
    }

}
