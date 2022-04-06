package com.company.controller;

import com.company.dto.MedicineDTO;
import com.company.dto.PharmacyDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Profile")
public class ProfileController {
    @Autowired
    private ProfileServise profileService;

    @PostMapping("")
    @ApiOperation(value = "create method", notes = "Bunda Profile create qilinadi faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE, ProfileRole.ADMIN_ROLE);
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda Profile update qilinadi id orqali  faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id, @Valid @RequestBody ProfileDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE, ProfileRole.USER_ROLE);
        ProfileDTO dto1 = profileService.update(dto, id);
        return ResponseEntity.ok(dto1);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor Profile ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getProfileById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO response = profileService.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  Profile Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE, ProfileRole.USER_ROLE);
        profileService.delete(id);
        return "Succesfully";
    }

    @GetMapping("/allprofile")
    @ApiOperation(value = "Profile List method", notes = "Bunda bor ProfileLar Olinadi faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> AllProfile(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE, ProfileRole.ADMIN_ROLE);
        List<ProfileDTO> list = profileService.AllProfile();
        return ResponseEntity.ok(list);
    }

    //email phone apteka password
    @GetMapping("getprofilebyemail/{email}")
    @ApiOperation(value = "get method", notes = "Bunda bor Profile ni olish uchun ishlatiladi email orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getProfileByEmail(HttpServletRequest request, @PathVariable("email") String email) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO dto = profileService.getProfileByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getprofilebyphone/{phone}")
    @ApiOperation(value = "get method", notes = "Bunda bor Profile ni olish uchun ishlatiladi phone orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getProfileByPhone(HttpServletRequest request, @PathVariable("phone") String phone) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO dto = profileService.getProfileByPhone(phone);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getprofilebypswdandlogin/{pswd}/{login}")
    @ApiOperation(value = "get method", notes = "Bunda bor Profile ni olish uchun ishlatiladi pswd va login  orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getProfileByPswdAndLogin(HttpServletRequest request, @PathVariable("pswd") String pswd, @PathVariable("login") String login) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO dto = profileService.getProfileByPswAndLogin(pswd, login);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getprofileaptekaid/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor Profile ni olish uchun ishlatiladi PharmacyId orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getProfileByAptekaId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<ProfileDTO> response = profileService.getProfileByAptekaId(id);
        return ResponseEntity.ok(response);
    }
}
