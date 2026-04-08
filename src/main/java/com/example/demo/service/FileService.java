package com.example.demo.service;

import com.example.demo.dto.FileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileService {
    FileResponseDto uploadFile(Long userId, MultipartFile file, String description);
    Resource downloadFile(Long fileId) throws java.net.MalformedURLException;  // добавил throws
    List<FileResponseDto> getUserFiles(Long userId);
    void deleteFile(Long fileId);

}