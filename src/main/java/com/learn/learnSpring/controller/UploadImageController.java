package com.learn.learnSpring.controller;


import com.learn.learnSpring.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadImageController {
    @Autowired
    UploadImageService uploadImageService;

    @PostMapping("/upload-image")
    ResponseEntity<?> uploadImages(@RequestPart("image") MultipartFile file) {
        return  uploadImageService.uploadImage(file);
    }

 /*   @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        try {
            Path path = Paths.get("/home/arathor/Dew/Iteli_j/spring_project/image-upload/" + filename);
            byte[] data = Files.readAllBytes(path);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            return ResponseEntity.ok().headers(headers).body(data);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }


    }*/


    @GetMapping("/download")
    public ResponseEntity<?> downloadedFile(@RequestParam String filename) {
        return  uploadImageService.downLoadImage(filename);
    }
}
