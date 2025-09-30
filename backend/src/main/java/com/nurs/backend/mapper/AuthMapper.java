package com.nurs.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.model.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  User toEntity(AuthRequest dto);
}
