package com.skaotico.servicio.rest.usuarioRol.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UsuarioRolResponseDto {
    private Long usuarioId;
    private Long rolId;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;


}