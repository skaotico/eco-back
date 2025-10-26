package com.skaotico.servicio.rest.accesoRecinto.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * Clave primaria compuesta para la entidad AccesoRecinto.
 * Representa la combinación de usuario_id y recinto_id.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoRecintoId {

    private Long usuarioId;
    private Long recintoId;
}
