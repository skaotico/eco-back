package com.skaotico.servicio.rest.accesoRecinto.service;

import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoResponseDto;

import java.util.List;
import java.util.Optional;

public interface AccesoRecintoService {

    AccesoRecintoResponseDto guardar(AccesoRecintoRequestDto request);

    AccesoRecintoResponseDto buscarPorId(Long usuarioId, Long recintoId);

    List<AccesoRecintoResponseDto> listarPorUsuario(Long usuarioId);

    List<AccesoRecintoResponseDto> listarPorRecinto(Long recintoId);

    void eliminar(Long usuarioId, Long recintoId);
}
