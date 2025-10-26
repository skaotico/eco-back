package com.skaotico.servicio.rest.rol.dto;

import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponseDto {

    private Long id;


    private RolUsuarioEnum nombre;


    private String descripcion;
}
