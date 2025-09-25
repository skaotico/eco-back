package com.skaotico.servicio.rest.area.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "recinto_id", nullable = false)
    private Long recintoId;

    @Column(name = "superficie_m2")
    private Double superficieM2;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_area")
    private TipoAreaEnum tipoArea;

    @OneToMany(mappedBy = "area")
    @JsonBackReference
    private List<ArbolModel> arboles;

}
