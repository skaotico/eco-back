package com.skaotico.servicio.rest.usuarioRol.mapper;


import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolRequestDto;
import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolResponseDto;
import com.skaotico.servicio.rest.usuarioRol.model.UsuarioRolModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioRolMapper {
    UsuarioRolMapper INSTANCE = Mappers.getMapper(UsuarioRolMapper.class);

    UsuarioRolModel toEntity(UsuarioRolRequestDto usuarioRolRequestDto);
    UsuarioRolResponseDto toDto(UsuarioRolModel usuarioRolModel);
}