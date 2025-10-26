package com.skaotico.servicio.rest.rol.service.impl;


import com.skaotico.servicio.rest.rol.dto.RolRequestDto;
import com.skaotico.servicio.rest.rol.dto.RolResponseDto;
import com.skaotico.servicio.rest.rol.mapper.RolMapper;
import com.skaotico.servicio.rest.rol.model.RolModel;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.rol.repository.RolRepository;
import com.skaotico.servicio.rest.rol.service.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;
    private static final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);

    public RolServiceImpl(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public RolResponseDto crearRol(RolRequestDto rolDTO) {
        RolModel rolModel =  rolMapper.toModel(rolDTO);
        return rolMapper.toResponse(rolRepository.save(rolModel));
    }

    @Override
    public RolResponseDto actualizarRol(Long id, RolRequestDto rolDtoActualizado) {
        RolModel rolModelActualizado = rolMapper.toModel(rolDtoActualizado);

        RolModel actualizado = rolRepository.findById(id)
                .map(rolExistente -> {
                    rolExistente.setNombre(rolModelActualizado.getNombre());
                    rolExistente.setDescripcion(rolModelActualizado.getDescripcion());
                    return rolRepository.save(rolExistente);
                })
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        return rolMapper.toResponse(actualizado);
    }

    @Override
    public boolean eliminarRol(Long id) {
        logger.info("Eliminando rol con id: {}", id);
        if (!rolRepository.existsById(id)) {
            logger.error("No se encontró rol para eliminar con id: {}", id);
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
        return true;

    }

    @Override
    public  RolResponseDto obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

    }

    @Override
    public RolResponseDto obtenerRolPorNombre(String nombre) {
        RolUsuarioEnum rolEnum = RolUsuarioEnum.fromString(nombre);

        // Desempaquetar Optional o lanzar excepción si no existe
        RolModel rol = rolRepository.findByNombre(rolEnum)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Mapear a Response DTO
        return rolMapper.toResponse(rol);
    }

    @Override
    public List<RolResponseDto> obtenerTodosLosRoles() {
        logger.debug("Obteniendo todos los roles");
        return  rolMapper.toResponse( rolRepository.findAll());
    }
}
