package com.learn.learnSpring.controller;


import com.learn.learnSpring.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadImageController {
    @Autowired
    UploadImageService uploadImageService;

    @PostMapping("/upload-image")
    ResponseEntity<?> uploadImages(@RequestPart("image") MultipartFile file) {
        return  uploadImageService.uploadImage(file);
    }

}
