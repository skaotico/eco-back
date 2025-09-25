package com.skaotico.servicio.rest.recinto.recinto.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para recibir datos al crear/actualizar accesos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoRecintoRequestDto {

    private Long usuarioId;
    private Long recintoId;

    private String permisos;
    private LocalDate fechaInicio;
    private LocalDate  fechaFin;
}
