package com.skaotico.servicio.rest.lectura.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturaArbolDTO {

    private Long arbolId;
    private Long tutorId;
    private BigDecimal humedad;
    private Boolean iniciaRiego;


}
