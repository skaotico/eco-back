package com.skaotico.servicio.rest.area.model;

/**
 * Enum que representa los distintos tipos de áreas.
 * Corresponde al tipo PostgreSQL tipo_area_enum.
 */
public enum TipoAreaEnum {
    INVERNADERO("Invernadero"),
    EXTERIOR("Exterior"),
    INTERIOR("Interior"),
    HIDROPONICO("Hidropónico");

    private final String label;

    TipoAreaEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}