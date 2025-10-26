package com.skaotico.servicio.rest.accesoRecinto.service.impl;

import com.skaotico.servicio.rest.recinto.model.RecintoModel;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoResponseDto;
import com.skaotico.servicio.rest.accesoRecinto.mapper.AccesoRecintoMapper;
import com.skaotico.servicio.rest.accesoRecinto.model.AccesoRecintoId;
import com.skaotico.servicio.rest.accesoRecinto.model.AccesoRecintoModel;
import com.skaotico.servicio.rest.accesoRecinto.repository.AccesoRecintoRepository;
import com.skaotico.servicio.rest.accesoRecinto.service.AccesoRecintoService;

import com.skaotico.servicio.rest.usuario.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccesoRecintoServiceImpl implements AccesoRecintoService {

    private final AccesoRecintoRepository repository;
    private final AccesoRecintoMapper mapper;

    @Override
    public AccesoRecintoResponseDto guardar(AccesoRecintoRequestDto request) {
        // Aquí normalmente buscarías Usuario y Recinto en sus repositorios
        Usuario usuario = new Usuario();
        usuario.setId(request.getUsuarioId());

        RecintoModel recinto = new RecintoModel();
        recinto.setId(request.getRecintoId());

        AccesoRecintoModel entity = mapper.toEntity(request, usuario, recinto);
        AccesoRecintoModel guardado = repository.save(entity);
        return mapper.toResponseDto(guardado);
    }

    @Override
    public AccesoRecintoResponseDto buscarPorId(Long usuarioId, Long recintoId) {
        AccesoRecintoId id = new AccesoRecintoId(usuarioId, recintoId);

        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("AccesoRecinto no encontrado para usuarioId: "
                        + usuarioId + " y recintoId: " + recintoId));
    }


    @Override
    public List<AccesoRecintoResponseDto> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccesoRecintoResponseDto> listarPorRecinto(Long recintoId) {
        System.out.println("llego "+recintoId);
        return repository.findByRecintoId(recintoId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long usuarioId, Long recintoId) {
        AccesoRecintoId id = new AccesoRecintoId(usuarioId, recintoId);
        repository.deleteById(id);
    }
}
