package com.skaotico.servicio.rest.usuarioRol.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioRolRequestDto {
    private Long usuarioId;
    private Long rolId;


}