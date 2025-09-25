package com.skaotico.servicio.rest.arbol.dto;

import com.fasterxml.jackson.databind.JsonNode;

import com.skaotico.servicio.rest.area.dto.AreaDto;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArbolResponseDto {

    private Long id;
    private String especie;
    private Long areaId;
    private String gpsPoint;
    private JsonNode metadata;


    private UsuarioCreateDTO creadoPor;

    private AreaDto area;
}