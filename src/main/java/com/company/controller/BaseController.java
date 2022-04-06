package com.company.controller;

import com.company.dto.BaseDTO;
import com.company.dto.CommentDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.BaseServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/korzinka")
public class BaseController {
    @Autowired
    private BaseServise baseServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda base create qilinadi faqat Userlar ", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody BaseDTO korzinkaDTO) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        BaseDTO korzinkaDTO1 = baseServise.create(korzinkaDTO);
        return ResponseEntity.ok(korzinkaDTO1);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda base update qilinadi id orqali  faqat Userlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id, @Valid
                                    @RequestBody BaseDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        BaseDTO entity = baseServise.update(dto, id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get BaseById method", notes = "Bunda bor base ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getBaseById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        BaseDTO response = baseServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  base Delete qilinadi id orqali  faqat Userlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        baseServise.delete(id);
        return "Succesfully";
    }

    @DeleteMapping("/deleteMedicineid/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  base Delete qilinadi MedicineId orqali  faqat Userlar ", nickname = "NickName")
    public String DeleteByMedicineId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        baseServise.deleteByMedicineId(id);
        return "Succesfully";
    }

    @DeleteMapping("/deleteProfileid/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda base Delete qilinadi ProfileId orqali  faqat Userlar ", nickname = "NickName")
    public String DeleteByProfileId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        baseServise.deleteByProfileId(id);
        return "Succesfully";
    }

    @DeleteMapping("/deletePharmacyid/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda base Delete qilinadi PharmacyId orqali  faqat Userlar ", nickname = "NickName")
    public String DeleteByPharmacyId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        baseServise.deleteByPharmacyId(id);
        return "Succesfully";
    }


    @GetMapping("/allBase")
    @ApiOperation(value = "Base List method", notes = "Bunda bor Baselar Olinadi  faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> AllBase(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<BaseDTO> list = baseServise.AllApteka();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getbasemedicineid/{id}")
    @ApiOperation(value = "get BaseByMedicineId method", notes = "Bunda bor base ni MedicineId bo'yicha olish uchun ishlatiladi faqat Adminlar lar ", nickname = "NickName")
    public ResponseEntity<?> getBaseByMedicineId(HttpServletRequest request, @PathVariable("id") Integer id,
                                                 @RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<BaseDTO> list = baseServise.getBaseByMedicineId(id, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getbaseprofileId/{id}")
    @ApiOperation(value = "get BaseByProfileId method", notes = "Bunda bor base ni ProfileId bo'yicha olish uchun ishlatiladi faqat Adminlar lar ", nickname = "NickName")
    public ResponseEntity<?> getBaseByProfileId(HttpServletRequest request, @PathVariable("id") Integer id,
                                                @RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<BaseDTO> response = baseServise.getBaseByProfileId(id, size, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbasepharmcyId/{id}")
    @ApiOperation(value = "get BaseByPharmacyId method", notes = "Bunda bor base ni PharmacyId bo'yicha olish uchun ishlatiladi faqat Adminlar lar ", nickname = "NickName")
    public ResponseEntity<?> getBaseByPharmacyId(HttpServletRequest request, @PathVariable("id") Integer id,
                                                 @RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<BaseDTO> response = baseServise.getBaseByPharmacyId(id, size, page);
        return ResponseEntity.ok(response);
    }

}
