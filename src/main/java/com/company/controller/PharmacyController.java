package com.company.controller;

import com.company.dto.PharmacyDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.PharmacyServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Apteka")
public class PharmacyController {
    @Autowired
    private PharmacyServise aptekaServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda Pharmacy create qilinadi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody PharmacyDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyDTO dto1 = aptekaServise.create(dto);
        return ResponseEntity.ok(dto1);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda Pharmacy update qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id, @Valid @RequestBody PharmacyDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyDTO entity = aptekaServise.update(dto, id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor Pharmacy ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getPharmacyById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyDTO response = aptekaServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  Pharmacy Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        aptekaServise.delete(id);
        return "Succesfully";
    }

    @GetMapping("/allapteka")
    @ApiOperation(value = "Medicine List method", notes = "Bunda bor Pharmacylar Olinadi faqat Hamma", nickname = "NickName")
    public ResponseEntity<?> AllApteka(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        List<PharmacyDTO> list = aptekaServise.AllApteka();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getbyphone/{phone}")
    @ApiOperation(value = "get method", notes = "Bunda bor Pharmacy ni olish uchun ishlatiladi phone orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getPharmacyByPhone(HttpServletRequest request, @PathVariable("phone") String phone) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyDTO dto = aptekaServise.getPharmacyByPhone(phone);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getbyemail/{email}")
    @ApiOperation(value = "get method", notes = "Bunda bor Pharmacy ni olish uchun ishlatiladi email orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getPharmacyByEmail(HttpServletRequest request, @PathVariable("email") String email) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyDTO dto = aptekaServise.getPharmacyByPhone(email);
        return ResponseEntity.ok(dto);
    }

}
