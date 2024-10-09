package com.learn.learnSpring.service.impl;

import com.learn.learnSpring.entities.UploadImage;
import com.learn.learnSpring.repository.UploadImageRepository;
import com.learn.learnSpring.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    @Autowired
    private UploadImageRepository uploadImageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResponseEntity<?> uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a file.");
        }

        try {
            String filePath = saveImage(file);
            UploadImage uploadImage = new UploadImage();
            uploadImage.setImagePath(filePath);
            uploadImageRepository.save(uploadImage); // Save the image path to the database
            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());

        }
    }


    private String saveImage(MultipartFile file) throws IOException {
        // Create the upload directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if needed
        }

        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);

        // Copy the file to the target location
        Files.copy(file.getInputStream(), path);
        return fileName; // Return the filename or path as needed
    }
}


