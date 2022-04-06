package com.company.service;

import com.company.dto.AdminRegistrationDTO;
import com.company.dto.AuthorizationDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.entity.PharmacyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServise {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailServise servise;
    @Autowired
    private PharmacyServise pharmacyServise;

    public ProfileDTO authorization(AuthorizationDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        Optional<ProfileEntity> optional = profileRepository
                .findByLoginAndPswd(dto.getLogin(), pswd);
        if (!optional.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("You are Not Alowed");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.createJwt(optional.get().getId(), optional.get().getRole()));
        return profileDTO;
    }

    public RegistrationDTO registration(RegistrationDTO dto) {
        PharmacyEntity pharmacy = pharmacyServise.get(dto.getAptekaId());

        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email is already exsists");
        }
        if (profileRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new BadRequestException("Login already exists");
        }
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        System.out.println(pswd);
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.CREATED);
        entity.setRole(ProfileRole.USER_ROLE);
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setApteka(pharmacy);
        entity.setPhone(dto.getPhone());
        entity.setLastActiveDate(LocalDateTime.now());
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        dto.setId(entity.getId());

        String jwt = JwtUtil.createJwt(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar qalaysan\n");
        builder.append("Agar bu sen bo'lsang shu linkka bos  ");
        builder.append("http://localhost:8080/auth/verification/" + jwt);
        servise.sendEmail(dto.getEmail(), "Registration ArzonApteka ", builder.toString());

        return dto;
    }

    public AdminRegistrationDTO createAdmin(AdminRegistrationDTO dto) {

        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email is already exsists");
        }
        if (profileRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new BadRequestException("Login already exists");
        }
        String pswd = DigestUtils.md5Hex(dto.getPassword());


        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.CREATED);
        entity.setRole(ProfileRole.ADMIN_ROLE);
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setPhone(dto.getPhone());
        entity.setLastActiveDate(LocalDateTime.now());
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        dto.setId(entity.getId());

        String jwt = JwtUtil.createJwt(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar qalaysan\n");
        builder.append(" shu linkka bos  ");
        builder.append("http://localhost:8080/auth/verification/" + jwt);
        servise.sendEmail(dto.getEmail(), "Registration ArzonApteka ", builder.toString());

        return dto;
    }

    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtAndId(jwt);

        Optional<ProfileEntity> entity = profileRepository.findById(id);
        if (entity.get().getStatus().equals(ProfileStatus.BLOCK)) {
            throw new BadRequestException("You are is BLOCK");
        }

        if (!entity.isPresent()) {
            throw new BadRequestException("User Not Found");
        }
        entity.get().setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity.get());
    }
}
