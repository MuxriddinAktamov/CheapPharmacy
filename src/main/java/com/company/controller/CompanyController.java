package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.CompanyDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.entity.CommentEntity;
import com.company.enums.ProfileRole;
import com.company.service.CompanyServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyServise companyServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda company create qilinadi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody CompanyDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CompanyDTO companyDTO = companyServise.create(dto);
        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda company update qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id, @Valid @RequestBody CompanyDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CompanyDTO dto1 = companyServise.update(dto, id);
        return ResponseEntity.ok(dto1);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor company ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getCompanyById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CompanyDTO response = companyServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  company Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        companyServise.delete(id);
        return "Succesfully";
    }

    @GetMapping("/allCompany")
    @ApiOperation(value = "company List method", notes = "Bunda bor companylar Olinadi  faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> AllCompany(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE, ProfileRole.ADMIN_ROLE);
        List<CompanyDTO> list = companyServise.AllCompany();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getphone/{phone}")
    @ApiOperation(value = "get method", notes = "Bunda bor company ni olish uchun ishlatiladi phone orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getCompanyByPhone(HttpServletRequest request, @PathVariable("phone") String phone) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CompanyDTO response = companyServise.getByPhone(phone);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getemail/{email}")
    @ApiOperation(value = "get method", notes = "Bunda bor company ni olish uchun ishlatiladi email orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getCompanyByEmail(HttpServletRequest request, @PathVariable("email") String email) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CompanyDTO response = companyServise.getByEmail(email);
        return ResponseEntity.ok(response);
    }
}
