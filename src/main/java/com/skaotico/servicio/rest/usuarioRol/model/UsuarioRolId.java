package com.skaotico.servicio.rest.usuarioRol.model;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioRolId   {

    private Long usuarioId;
    private Long rolId;

    public UsuarioRolId() {}

    public UsuarioRolId(Long usuarioId, Long rolId) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioRolId)) return false;
        UsuarioRolId that = (UsuarioRolId) o;
        return usuarioId.equals(that.usuarioId) && rolId.equals(that.rolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, rolId);
    }
}