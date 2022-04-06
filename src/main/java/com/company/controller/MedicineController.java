package com.company.controller;

import com.company.dto.CompanyDTO;
import com.company.dto.MedicineDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.MedicineServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    private MedicineServise medicineServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda Medicine create qilinadi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody MedicineDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        MedicineDTO dto1 = medicineServise.create(dto);
        return ResponseEntity.ok(dto1);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda Medicine update qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable("id") Integer id,@Valid @RequestBody MedicineDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        MedicineDTO dto1 = medicineServise.update(dto, id);
        return ResponseEntity.ok(dto1);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor Medicine ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getMedicineById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        MedicineDTO response = medicineServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda Medicine Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        medicineServise.delete(id);
        return "Succesfully";
    }

    @GetMapping("/allMedicine")
    @ApiOperation(value = "Medicine List method", notes = "Bunda bor Medicinelar olinadi  faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> AllMedicine(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE,ProfileRole.ADMIN_ROLE);
        List<MedicineDTO> list = medicineServise.AllMedicine();
        return ResponseEntity.ok(list);
    }

    @GetMapping("getmedicinebyname/{name}")
    @ApiOperation(value = "get method", notes = "Bunda bor Medicine ni olish uchun ishlatiladi name orqali  hamma Ola oladi", nickname = "NickName")
    public ResponseEntity<?> getMedicineByName(HttpServletRequest request, @PathVariable("name") String name,
                                               @RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        List<MedicineDTO> medicineDTOS = medicineServise.getByMedicineName(name, size, page);
        return ResponseEntity.ok(medicineDTOS);
    }

    @GetMapping("/getmedicinebycompanyid/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor Medicine ni olish uchun ishlatiladi CompanyId orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getMedicineByCompanyId(HttpServletRequest request, @PathVariable("id") Integer id,
                                                    @RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<MedicineDTO> response = medicineServise.getByCompanyId(id,size,page);
        return ResponseEntity.ok(response);
        // name,companyId bo'yicha update va delete qilish
    }

}
