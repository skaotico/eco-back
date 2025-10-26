package com.skaotico.servicio.rest.recinto.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un Recinto dentro del sistema.
 * Contiene información básica como nombre, ubicación, capacidad de áreas y descripción.
 */
@Entity
@Table(name = "recinto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecintoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String ubicacion;

    private Integer capacidadAreas;

    private String descripcion;


}
