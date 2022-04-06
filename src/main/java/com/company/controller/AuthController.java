package com.company.controller;

import com.company.dto.*;
import com.company.enums.EmailUsedStatus;
import com.company.enums.ProfileRole;
import com.company.service.AuthServise;
import com.company.service.EmailHistoryServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServise authServise;
    @Autowired
    private EmailHistoryServise emailHistoryServise;

    @PostMapping
    @ApiOperation(value = "Auth method", notes = "Bunda profile auth qiladi", nickname = "NickName")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authServise.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "registration method", notes = "Bunda profile registrationdan o'tadi ", nickname = "NickName")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO dto) {
        RegistrationDTO response = authServise.registration(dto);
        emailHistoryServise.createEmailHistory(dto.getEmail(), EmailUsedStatus.NOT_USED);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @ApiOperation(value = "create Admin method", notes = "Bunda article create qilinadi faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminRegistrationDTO dto) {
        AdminRegistrationDTO response = authServise.createAdmin(dto);
        emailHistoryServise.createEmailHistory(dto.getEmail(), EmailUsedStatus.NOT_USED);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/{jwt}")
    @ApiOperation(value = "Registration Verification method", notes = "Verification method", nickname = "NickName")
    public ResponseEntity<RegistrationDTO> verification(@PathVariable("jwt") String jwt) {
        authServise.verification(jwt);
        emailHistoryServise.updateStatus(jwt);
        return ResponseEntity.ok().build();

    }
}
