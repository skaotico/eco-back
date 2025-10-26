package com.skaotico.servicio.rest.rol.service;


import com.skaotico.servicio.rest.rol.dto.RolRequestDto;
import com.skaotico.servicio.rest.rol.dto.RolResponseDto;
import com.skaotico.servicio.rest.rol.model.RolModel;

import java.util.List;
import java.util.Optional;

public interface RolService {

    RolResponseDto crearRol(RolRequestDto rolRequestDto);

    RolResponseDto actualizarRol(Long id, RolRequestDto rolRequestDto);

    boolean eliminarRol(Long id);

    RolResponseDto  obtenerRolPorId(Long id);

    RolResponseDto obtenerRolPorNombre(String nombre);

    List<RolResponseDto> obtenerTodosLosRoles();
}
