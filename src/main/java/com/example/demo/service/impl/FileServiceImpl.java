package com.example.demo.service.impl;

import com.example.demo.config.FileStorageConfig;
import com.example.demo.dto.FileResponseDto;
import com.example.demo.mapper.FileMapper;
import com.example.demo.model.FileEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Lombok
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FileMapper fileMapper;
    private final FileStorageConfig storageConfig;

    // Lombok сгенерирует сам

    @Override
    public FileResponseDto uploadFile(Long userId, MultipartFile file, String description) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String originalFileName = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        String filePath = Paths.get(storageConfig.getUploadDir(), storedFileName).toString();

        try {
            Files.createDirectories(Paths.get(storageConfig.getUploadDir()));
            file.transferTo(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(originalFileName);
        fileEntity.setStoredFileName(storedFileName);
        fileEntity.setFilePath(filePath);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setContentType(file.getContentType());
        fileEntity.setUser(user);

        FileEntity savedFile = fileRepository.save(fileEntity);
        return fileMapper.toDto(savedFile);
    }

    @Override
    public Resource downloadFile(Long fileId) throws java.net.MalformedURLException {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Path filePath = Paths.get(fileEntity.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("File not readable");
        }
        return resource;
    }

    @Override
    public List<FileResponseDto> getUserFiles(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return fileRepository.findByUser(user)
                .stream()
                .map(fileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        try {
            Files.deleteIfExists(Paths.get(fileEntity.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }

        fileRepository.delete(fileEntity);
    }
}