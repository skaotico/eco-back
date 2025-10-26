package com.skaotico.servicio.rest.usuarioRol.service.impl;

import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolRequestDto;
import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolResponseDto;
import com.skaotico.servicio.rest.usuarioRol.mapper.UsuarioRolMapper;
import com.skaotico.servicio.rest.usuarioRol.model.UsuarioRolId;
import com.skaotico.servicio.rest.usuarioRol.model.UsuarioRolModel;
import com.skaotico.servicio.rest.usuarioRol.repository.UsuarioRolRepository;
import com.skaotico.servicio.rest.usuarioRol.service.UsuarioRolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioRolServiceImpl implements UsuarioRolService {

    private final UsuarioRolRepository repository;
    private final UsuarioRolMapper mapper;

    public UsuarioRolServiceImpl(UsuarioRolRepository repository, UsuarioRolMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioRolResponseDto create(UsuarioRolRequestDto dto) {
        UsuarioRolModel entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public List<UsuarioRolResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long usuarioId, Long rolId) {
        UsuarioRolId id = new UsuarioRolId(usuarioId, rolId);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<UsuarioRolResponseDto> getRolPorUsuarioID(Long usuarioId) {

        List<UsuarioRolModel> roles = repository.findByUsuarioId(usuarioId);


        return roles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}