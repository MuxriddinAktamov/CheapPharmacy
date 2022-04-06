package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentServise;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentServise commentServise;

    @PostMapping("/create")
    @ApiOperation(value = "create method", notes = "Bunda comment create qilinadi faqat Userlar va Adminlar", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request,@Valid @RequestBody CommentDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE,ProfileRole.USER_ROLE);
        CommentDTO commentDTO = commentServise.create(dto);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda comment update qilinadi id orqali  faqat Userlar ", nickname = "NickName")
    public ResponseEntity<CommentDTO> updateCommentByAdmin(HttpServletRequest request,
                                                           @PathVariable("id") Integer id,@Valid @RequestBody CommentDTO dto) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE,ProfileRole.USER_ROLE);
        CommentDTO entity = commentServise.update(dto, id);
        return ResponseEntity.ok(entity);
    }
    @GetMapping("/get/{id}")
    @ApiOperation(value = "get method", notes = "Bunda bor comment ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getCommentById(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        CommentDTO response = commentServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  comment Delete qilinadi id orqali  faqat Userlar ", nickname = "NickName")
    public String Delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request,ProfileRole.USER_ROLE);
        commentServise.delete(id);
        return "Succesfully";
    }
    @GetMapping("/allComment")
    @ApiOperation(value = "comment List method", notes = "Bunda bor commentlar Olinadi  faqat Adminlar va Userlar", nickname = "NickName")
    public ResponseEntity<?> AllComment(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.USER_ROLE,ProfileRole.ADMIN_ROLE);
        List<CommentDTO> list = commentServise.AllApteka();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getmedicineid/{id}")
    @ApiOperation(value = "get commentByMedicineId method", notes = "Bunda bor comment ni MedicineId bo'yicha olish uchun ishlatiladi faqat Adminlar va Userlar ", nickname = "NickName")
    public ResponseEntity<?> getMedicineId(HttpServletRequest request, @PathVariable("id") Integer id,@RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE,ProfileRole.USER_ROLE);
        List<CommentDTO> response = commentServise.getByMedicineId(id,size,page);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getprofileid/{id}")
    @ApiOperation(value = "get commentByProfileId method", notes = "Bunda bor comment ni ProfileId bo'yicha olish uchun ishlatiladi faqat Adminlar va Userlar ", nickname = "NickName")
    public ResponseEntity<?> getProfileId(HttpServletRequest request, @PathVariable("id") Integer id,@RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE,ProfileRole.USER_ROLE);
        List<CommentDTO> response = commentServise.getByProfileId(id,size,page);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getpharmacyid/{id}")
    @ApiOperation(value = "get commentByPharmacyid method", notes = "Bunda bor comment ni PharmacyId bo'yicha olish uchun ishlatiladi faqat Adminlar", nickname = "NickName")
    public ResponseEntity<?> getPharmacyId(HttpServletRequest request, @PathVariable("id") Integer id,@RequestParam("page") int page, @RequestParam("size") int size) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<CommentDTO> response = commentServise.getByPharmacyId(id,size,page);
        return ResponseEntity.ok(response);
    }

}
