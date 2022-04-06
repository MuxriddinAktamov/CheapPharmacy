package com.company.controller;

import com.company.dto.PharmacyDTO;
import com.company.dto.PharmacyMedicineDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.PharmacyMedicineServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/AptekaMedicine")
public class PharmacyMedicineController {

    @Autowired
    private PharmacyMedicineServise pharmacyMedicineServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda PharmacyMedicine create qilinadi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody PharmacyMedicineDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyMedicineDTO dto1 = pharmacyMedicineServise.create(dto);
        return ResponseEntity.ok(dto1);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda PharmacyMedicine update qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> update(HttpServletRequest request,@Valid @RequestBody PharmacyMedicineDTO dto,
                                    @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyMedicineDTO dto1 = pharmacyMedicineServise.update(dto, id);
        return ResponseEntity.ok(dto1);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor PharmacyMedicine ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> get(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyMedicineDTO dto = pharmacyMedicineServise.getById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  PharmacyMedicine Delete qilinadi id orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        PharmacyMedicineDTO dto = pharmacyMedicineServise.delete(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getByMedicineId/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor PharmacyMedicine ni olish uchun ishlatiladi MedicineId orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getByMedicineId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<PharmacyMedicineDTO> list = pharmacyMedicineServise.getByMedicineId(id);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/getByPharmacyId/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor PharmacyMedicine ni olish uchun ishlatiladi PharmacyId orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getByPharmacyId(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<PharmacyMedicineDTO> list = pharmacyMedicineServise.getByPharmacyId(id);
        return ResponseEntity.ok(list);
    }

    // AllPharmacyMedicine method
}
