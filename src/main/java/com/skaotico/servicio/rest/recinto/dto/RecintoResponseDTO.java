package com.skaotico.servicio.rest.recinto.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecintoResponseDTO {

    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer capacidadAreas;
    private String descripcion;

}
