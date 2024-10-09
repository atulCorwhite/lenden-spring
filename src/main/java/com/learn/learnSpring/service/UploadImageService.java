package com.learn.learnSpring.service;

import com.learn.learnSpring.entities.GroupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

    ResponseEntity<?> uploadImage(MultipartFile file);
}
