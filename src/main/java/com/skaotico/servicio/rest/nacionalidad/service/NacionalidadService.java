package com.skaotico.servicio.rest.nacionalidad.service;

import com.skaotico.servicio.rest.nacionalidad.dto.NacionalidadDto;


import java.util.List;

public interface NacionalidadService {


    /**
     * Lista todas las nacionalidades existentes.
     *
     * @return Lista de Nacionalidad DTOs
     */
    List<NacionalidadDto> listarNacionalidades();

    List<NacionalidadDto> insertarNacionalidadesMasivas(List<NacionalidadDto> nacionalidades);
}
