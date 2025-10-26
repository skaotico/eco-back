package com.skaotico.servicio.rest.rol.mapper;

import com.skaotico.servicio.rest.rol.dto.RolRequestDto;
import com.skaotico.servicio.rest.rol.dto.RolResponseDto;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.util.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper extends BaseMapper<RolRequestDto, RolModel> {

    @Override
    @Mapping(target = "id", ignore = true)
    RolModel toModel(RolRequestDto dto);

    RolResponseDto toResponse(RolModel model);

    List<RolResponseDto> toResponse(List<RolModel> models);
}
