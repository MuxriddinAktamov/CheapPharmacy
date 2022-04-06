package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;

import javax.imageio.ImageIO;
import javax.mail.Quota;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachServise {
    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String uploadFolder;

    @Value("${attach.open.url}")
    private String attachOpneUrl;

    public AttachDTO saveToSystem(MultipartFile file) {
        try {
            String filePath = getYmDString(); // 2021/07/13

            String fileType = file.getContentType().split("/")[1]; // png, jpg, jpeg
            String fileToken = UUID.randomUUID().toString();
            String fileUrl = filePath + "/" + fileToken + "." + fileType; // sdasdasdasdasdas.png

            // 2021/07/13/ + adsadasdasdasda + . + png
            File folder = new File(uploadFolder + "/" + filePath); //  uploads/2021/07/13/adsadasdasdasda.png
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // save to system
            Path path = Paths.get(uploadFolder + "/images/" + fileUrl);
            Files.copy(file.getInputStream(), path);

            AttachEntity entity = new AttachEntity();
            entity.setPath(filePath);
            entity.setType(fileType);
            entity.setSize(file.getSize());
            entity.setToken(fileToken);
//            entity.setUrl("http://localhost:8081/image/load/" + fileToken);
            attachRepository.save(entity);

            AttachDTO dto = new AttachDTO();
            dto.setPath(filePath);
            dto.setType(fileType);
            dto.setSize(file.getSize());
            dto.setToken(fileToken);
            dto.setUrl(attachOpneUrl + "/" + fileToken);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public static String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day + "/";
    }

    public String saveFileOne(MultipartFile file) {
        try {
            File folder = new File(uploadFolder + "/");
            if (!folder.exists()) {
                folder.mkdir();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFolder + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String saveFileTwo(MultipartFile file) {
        try {
            Path copyLocation = Paths.get(uploadFolder + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resource load(String fileName) {
        try {
            Path file = Paths.get(uploadFolder + "/" + fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public byte[] loadAttach(String fileName) {
        try {
            byte[] imageInByte;
            Optional<AttachEntity> entity = attachRepository.findByToken(fileName);
            if (!entity.isPresent()) {
                return new byte[0];
            }
            BufferedImage originalImage;
            String filePath = entity.get().getPath() + "/" + fileName + "." + entity.get().getType();
            try {
                originalImage = ImageIO.read(new File(uploadFolder + "/" + filePath));
            } catch (Exception e) {
                return new byte[0];
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.get().getType(), baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public void delete(String key) {
        Optional<AttachEntity> entity = attachRepository.findByToken(key);
        if (!entity.isPresent()) {
            return;
        }
        String filePath = entity.get().getPath() + "/" + key + "." + entity.get().getType();
        File file = new File(uploadFolder + "/" + filePath);
        if (file.exists()) {
            file.delete();
        }
        attachRepository.deleteByToken(key);
    }
}
