package com.example.demo.repository;

import com.example.demo.model.FileEntity;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUser(UserEntity user);
    Optional<FileEntity> findByStoredFileName(String storedFileName);
}