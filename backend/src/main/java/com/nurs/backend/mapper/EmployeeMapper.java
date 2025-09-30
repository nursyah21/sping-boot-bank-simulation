package com.nurs.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  @Mapping(target = "id", ignore = true)
  Employee toEntity(EmployeeRequest dto);
  
  @Mapping(target = "id", ignore = true)
  void updateEmployeeFromDto(EmployeeRequest dto, @MappingTarget Employee entity);
}
