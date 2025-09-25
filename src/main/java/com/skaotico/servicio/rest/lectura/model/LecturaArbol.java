package com.skaotico.servicio.rest.lectura.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lectura_arbol")
public class LecturaArbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arbol_id", nullable = false)
    private Long arbolId;

    @Column(name = "tutor_id")
    private Long tutorId;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humedad;

    @Column(name = "inicia_riego")
    @Builder.Default
    private Boolean iniciaRiego = false;



    @Column(name = "creado_en", nullable = false)
    @Builder.Default
    private LocalDateTime creadoEn = LocalDateTime.now();

    @Column(name = "actualizado_en", nullable = false)
    @Builder.Default
    private LocalDateTime actualizadoEn = LocalDateTime.now();


}
