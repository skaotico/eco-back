package com.skaotico.servicio.rest.arbol.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import java.util.Map;


@Getter
@Setter
@ToString
public class ArbolCreateDto {


    @NotNull
    private String especie;


    @NotNull
    private Long areaId;


    @NotNull
    private LatLngDto gpsPoint;


    private Map<String, Object> metadata;


}
