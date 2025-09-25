package com.skaotico.servicio.rest.arbol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.skaotico.servicio.rest.arbol.mapper.ArbolMapper;
import com.skaotico.servicio.rest.area.model.Area;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidad que representa un árbol en la base de datos.
 * <p>
 * Esta clase se mapea a la tabla {@code arbol} usando JPA/Hibernate.
 * Contiene información básica del árbol, ubicación y metadatos adicionales.
 * </p>
 *
 * <h2>Campos</h2>
 * <ul>
 *   <li><b>id</b>: Identificador único del árbol (PK, autogenerado).</li>
 *   <li><b>especie</b>: Nombre de la especie del árbol.</li>
 *   <li><b>area</b>: Relación ManyToOne con {@link Area}, representa el área donde se encuentra el árbol. Cargado de forma lazy y no serializado en JSON.</li>
 *   <li><b>gpsPoint</b>: Coordenadas del árbol en formato String "(lat,lng)".</li>
 *   <li><b>metadata</b>: Datos adicionales del árbol almacenados como JSONB en PostgreSQL.</li>
 *   <li><b>creadoPor</b>: Relación ManyToOne con {@link Usuario}, indica qué usuario creó el registro. Cargado de forma lazy y no serializado en JSON.</li>
 * </ul>
 *
 * <h2>Reglas de negocio y consideraciones</h2>
 * <ul>
 *   <li>La relación con {@link Area} es obligatoria ({@code optional=false}).</li>
 *   <li>El campo {@code metadata} permite almacenar información flexible adicional, como edad, altura, estado, etc.</li>
 *   <li>El campo {@code gpsPoint} se espera en formato "(lat,lng)" para compatibilidad con {@link ArbolMapper}.</li>
 *   <li>El campo {@code creadoPor} es opcional, pero permite auditar qué usuario registró el árbol.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * ArbolModel arbol = new ArbolModel();
 * arbol.setEspecie("Quercus robur");
 * arbol.setGpsPoint("(40.4168,-3.7038)");
 * arbol.setArea(areaRepository.findById(123L).orElseThrow());
 * arbol.setCreadoPor(usuarioRepository.findById(1L).orElseThrow());
 * ObjectMapper mapper = new ObjectMapper();
 * JsonNode meta = mapper.createObjectNode().put("edad", 10).put("altura", 5.2);
 * arbol.setMetadata(meta);
 *
 * arbolRepository.save(arbol);
 * }</pre>
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "arbol")
public class ArbolModel {

    /**
     * Identificador único del árbol.
     * <p>Clave primaria, autogenerada.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la especie del árbol.
     */
    private String especie;

    /**
     * Área donde se encuentra el árbol.
     * <p>Relación ManyToOne, cargada de forma lazy y no serializada en JSON.</p>
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    @JsonManagedReference
    private Area area;

    /**
     * Coordenadas geográficas del árbol en formato "(lat,lng)".
     */
    @Column(name = "gps_point")
    private String gpsPoint;

    /**
     * Metadatos adicionales del árbol, almacenados como JSONB en PostgreSQL.
     */
    @Column(name = "metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode metadata;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;
}
