package com.skaotico.servicio.rest.recinto.recinto.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO para devolver datos de accesos al cliente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoRecintoResponseDto {

    private Long usuarioId;
    private Long recintoId;

    private String permisos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
