package com.skaotico.servicio.rest.recinto.recinto.service.impl;

import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoResponseDto;
import com.skaotico.servicio.rest.recinto.recinto.mapper.AccesoRecintoMapper;
import com.skaotico.servicio.rest.recinto.recinto.model.AccesoRecintoId;
import com.skaotico.servicio.rest.recinto.recinto.model.AccesoRecintoModel;
import com.skaotico.servicio.rest.recinto.recinto.repository.AccesoRecintoRepository;
import com.skaotico.servicio.rest.recinto.recinto.service.AccesoRecintoService;
import com.skaotico.servicio.rest.recinto.model.Recinto;
import com.skaotico.servicio.rest.usuario.model.Usuario;
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

        Recinto recinto = new Recinto();
        recinto.setId(request.getRecintoId());

        AccesoRecintoModel entity = mapper.toEntity(request, usuario, recinto);
        AccesoRecintoModel guardado = repository.save(entity);
        return mapper.toResponseDto(guardado);
    }

    @Override
    public Optional<AccesoRecintoResponseDto> buscarPorId(Long usuarioId, Long recintoId) {
        AccesoRecintoId id = new AccesoRecintoId(usuarioId, recintoId);
        return repository.findById(id)
                .map(mapper::toResponseDto);
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
