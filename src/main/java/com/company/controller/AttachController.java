package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.service.AttachServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachServise attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        AttachDTO attachDTO = attachService.saveToSystem(file);
        /*String fileName = attachService.saveFileOne(file);
        return ResponseEntity.ok().body(fileName);*/
        return ResponseEntity.ok().body(attachDTO);
    }

    @GetMapping("/load/{fineName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable("fineName") String fileToken) {
        Resource file = attachService.load(fileToken);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/get/{fileName:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadAttach(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }
}
