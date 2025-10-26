package com.skaotico.servicio.rest.accesoRecinto.mapper;

import com.skaotico.servicio.rest.recinto.model.RecintoModel;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoResponseDto;
import com.skaotico.servicio.rest.accesoRecinto.model.AccesoRecintoModel;

import com.skaotico.servicio.rest.usuario.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper para convertir entre DTOs y entidad AccesoRecintoModel
 * utilizando MapStruct.
 */
@Mapper(componentModel = "spring")
public interface AccesoRecintoMapper {

    /**
     * Convierte un DTO de request en la entidad.
     * Se asignan Usuario y Recinto a partir de sus IDs.
     */
    @Mapping(source = "request.usuarioId", target = "usuario", qualifiedByName = "mapUsuarioIdToUsuario")
    @Mapping(source = "request.recintoId", target = "recinto", qualifiedByName = "mapRecintoIdToRecinto")
    @Mapping(target = "id", expression = "java(new AccesoRecintoId(request.getUsuarioId(), request.getRecintoId()))")
    AccesoRecintoModel toEntity(AccesoRecintoRequestDto request, Usuario usuario, RecintoModel recinto);

    /**
     * Convierte la entidad a DTO de respuesta.
     */
    @Mapping(source = "id.usuarioId", target = "usuarioId")
    @Mapping(source = "id.recintoId", target = "recintoId")
    AccesoRecintoResponseDto toResponseDto(AccesoRecintoModel entity);

    /**
     * Convierte un ID de usuario en un objeto Usuario con solo el ID seteado.
     */
    @Named("mapUsuarioIdToUsuario")
    default Usuario mapUsuarioIdToUsuario(Long usuarioId) {
        if (usuarioId == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return usuario;
    }

    /**
     * Convierte un ID de recinto en un objeto Recinto con solo el ID seteado.
     */
    @Named("mapRecintoIdToRecinto")
    default RecintoModel mapRecintoIdToRecinto(Long recintoId) {
        if (recintoId == null) return null;
        RecintoModel recinto = new RecintoModel();
        recinto.setId(recintoId);
        return recinto;
    }
}
