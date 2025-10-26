package com.skaotico.servicio.rest.usuarioRol.service;

import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolRequestDto;
import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolResponseDto;

import java.util.List;

public interface UsuarioRolService {
    UsuarioRolResponseDto create(UsuarioRolRequestDto dto);
    List<UsuarioRolResponseDto> getAll();
    Boolean delete(Long usuarioId, Long rolId);
    List<UsuarioRolResponseDto> getRolPorUsuarioID(Long usuarioId);
}
