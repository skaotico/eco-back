package com.skaotico.servicio.rest.usuarioRol.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "usuario_rol")
@IdClass(UsuarioRolId.class)
public class UsuarioRolModel {

    @Id
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Id
    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "creado_en", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    @UpdateTimestamp
    private LocalDateTime actualizadoEn;


}