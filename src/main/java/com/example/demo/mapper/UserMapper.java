package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(UserDto userDto);
}