package com.example.demo.mapper;

import com.example.demo.dto.FileResponseDto;
import com.example.demo.model.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(source = "user.id", target = "userId")

    FileResponseDto toDto(FileEntity entity);

    // файл создаётся через конструктор


}