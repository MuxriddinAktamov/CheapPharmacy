package com.company.service;

import com.company.dto.PharmacyDTO;
import com.company.dto.PharmacyFilterDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.entity.PharmacyEntity;
import com.company.entity.ProfileEntity;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.spec.PharmacySpecfication;
import com.company.spec.ProfileSpecification;
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
public class ProfileServise {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PharmacyServise pharmacyServise;

    public ProfileDTO create(ProfileDTO dto) {
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getId());

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setApteka(pharmacy);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhone(dto.getPhone());
        entity.setLastActiveDate(LocalDateTime.now());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        entity.setStatus(dto.getStatus());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Profile not found"));
    }

    public ProfileDTO update(ProfileDTO dto, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getId());
        ProfileEntity entity = get(id);

        entity.setName(dto.getName());
        entity.setApteka(pharmacy);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhone(dto.getPhone());
        entity.setLastActiveDate(LocalDateTime.now());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(dto.getPswd());
        entity.setStatus(dto.getStatus());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);
        return dto;
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = profileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Profile not found"));
        return toDTO(entity);
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAptekaId(entity.getApteka().getId());
        dto.setCreated_date(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());
        dto.setLastActive_date(entity.getLastActiveDate());
        dto.setSurname(entity.getSurname());
        dto.setLogin(entity.getLogin());
        dto.setPswd(entity.getPswd());
        dto.setStatus(entity.getStatus());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        ProfileEntity entity = get(id);
        profileRepository.delete(entity);
    }

    public List<ProfileDTO> AllProfile() {
        List<ProfileDTO> list = new LinkedList<>();
        Iterable<ProfileEntity> entities = profileRepository.findAll();
        Iterator<ProfileEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public ProfileDTO getProfileByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email is Null or Empty");
        }
        Optional<ProfileEntity> entity = profileRepository.findByEmail(email);
        ProfileDTO dto = toDTO(entity.get());
        return dto;
    }

    public ProfileDTO getProfileByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new BadRequestException("Email is Null or Empty");
        }
        Optional<ProfileEntity> entity = profileRepository.findByPhone(phone);
        ProfileDTO dto = toDTO(entity.get());
        return dto;
    }

    public ProfileDTO getProfileByPswAndLogin(String password, String login) {
        if (password == null || password.isEmpty() || login == null || login.isEmpty()) {
            throw new BadRequestException("Email is Null or Empty");
        }
        Optional<ProfileEntity> entity = profileRepository.findByLoginAndPswd(login, password);
        ProfileDTO dto = toDTO(entity.get());
        return dto;
    }

    public List<ProfileDTO> getProfileByAptekaId(Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is Null");
        }
        List<ProfileDTO> list = new LinkedList<>();
        List<ProfileEntity> entities = profileRepository.findByAptekaId(id);
        Iterator<ProfileEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            list.add(toDTO(iterator.next()));
        }
        return list;
    }

    public PageImpl<ProfileDTO> filterSpe(int page, int size, ProfileFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Specification<ProfileEntity> spec = Specification.where(ProfileSpecification.isNotNull("id"));
        if (filterDTO.getName() != null || !filterDTO.getName().isEmpty()) {
            spec.and(ProfileSpecification.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getSurname() != null || !filterDTO.getSurname().isEmpty()) {
            spec.and(ProfileSpecification.equal("surname", filterDTO.getSurname()));
        }
        if (filterDTO.getPhone() != null || !filterDTO.getPhone().isEmpty()) {
            spec.and(ProfileSpecification.equal("phone", filterDTO.getPhone()));
        }
        if (filterDTO.getEmail() != null || !filterDTO.getEmail().isEmpty()) {
            spec.and(ProfileSpecification.equal("email", filterDTO.getEmail()));
        }
        if (filterDTO.getLogin() != null || !filterDTO.getLogin().isEmpty()) {
            spec.and(ProfileSpecification.equal("login", filterDTO.getLogin()));
        }
        if (filterDTO.getPswd() != null || !filterDTO.getPswd().isEmpty()) {
            spec.and(ProfileSpecification.equal("pswd", filterDTO.getPswd()));
        }
        if (filterDTO.getAptekaId() != null) {
            spec.and(ProfileSpecification.id(filterDTO.getAptekaId()));
        }
        if (filterDTO.getRole() != null) {
            spec.and(ProfileSpecification.role(filterDTO.getRole()));
        }
        if (filterDTO.getStatus() != null) {
            spec.and(ProfileSpecification.status(filterDTO.getStatus()));
        }
        if (filterDTO.getToDate() != null && filterDTO.getFromDate() != null) {
            spec.and(ProfileSpecification.date(filterDTO.getFromDate(), filterDTO.getToDate()));
        }


        Page<ProfileEntity> profileEntityPage = profileRepository.findAll(spec, pageable);
        List<ProfileDTO> dtoList = profileEntityPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        System.out.println(profileEntityPage.getTotalElements());
        return new PageImpl<>(dtoList, pageable, profileEntityPage.getTotalElements());
    }
}
