package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.enums.ProfileRole;
import com.company.service.RegionServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionServise regionServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda region create qilinadi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request,@Valid @RequestBody RegionDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDTO region = regionServise.create(dto);
        return ResponseEntity.ok(region);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda region update qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id,@Valid @RequestBody RegionDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDTO dto1 = regionServise.update(dto, id);
        return ResponseEntity.ok(dto1);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor region ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getArticleById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDTO response = regionServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  region Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        regionServise.delete(id);
        return "Succesfully";
    }

    @GetMapping("/alldistrict")
    @ApiOperation(value = "region List method", notes = "Bunda bor region Olinadi faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> AllRegion(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE);
        List<RegionDTO> list = regionServise.AllRegion();
        return ResponseEntity.ok(list);
    }
}
