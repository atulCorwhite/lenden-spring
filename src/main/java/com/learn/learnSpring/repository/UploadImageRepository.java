package com.learn.learnSpring.repository;

import com.learn.learnSpring.entities.UploadImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadImageRepository extends JpaRepository<UploadImage,Long> {

}
