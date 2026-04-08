package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.FileResponseDto;
import com.example.demo.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/{userId}")
    public ResponseEntity<FileResponseDto> uploadFile(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description) {

        FileResponseDto response = fileService.uploadFile(userId, file, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        try {
            Resource resource = fileService.downloadFile(fileId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException("Invalid file URL", e);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileResponseDto>> getUserFiles(@PathVariable Long userId) {
        return ResponseEntity.ok(fileService.getUserFiles(userId));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();

    }
}