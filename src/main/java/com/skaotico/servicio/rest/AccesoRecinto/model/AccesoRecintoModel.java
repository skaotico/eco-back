package com.skaotico.servicio.rest.accesoRecinto.model;


import com.skaotico.servicio.rest.recinto.model.RecintoModel;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa el acceso de un usuario a un recinto,
 * con permisos y fechas de vigencia.
 */
@Entity
@Table(name = "acceso_recinto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoRecintoModel {

    @EmbeddedId
    private AccesoRecintoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recintoId")
    @JoinColumn(name = "recinto_id", nullable = false)
    private RecintoModel recinto;



    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @CreationTimestamp
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;
}
