package com.skaotico.servicio.rest.recinto.recinto.service;

import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoResponseDto;

import java.util.List;
import java.util.Optional;

public interface AccesoRecintoService {

    AccesoRecintoResponseDto guardar(AccesoRecintoRequestDto request);

    Optional<AccesoRecintoResponseDto> buscarPorId(Long usuarioId, Long recintoId);

    List<AccesoRecintoResponseDto> listarPorUsuario(Long usuarioId);

    List<AccesoRecintoResponseDto> listarPorRecinto(Long recintoId);

    void eliminar(Long usuarioId, Long recintoId);
}
