package com.skaotico.servicio.rest.usuario.type;

public enum GeneroEnum {
    MASCULINO("MASCULINO"),
    FEMENINO("FEMENINO"),
    OTRO("OTRO");

    private final String descripcion;

    GeneroEnum(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
