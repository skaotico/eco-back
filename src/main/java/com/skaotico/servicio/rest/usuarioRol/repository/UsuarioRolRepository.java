package com.skaotico.servicio.rest.usuarioRol.repository;


import com.skaotico.servicio.rest.usuarioRol.model.UsuarioRolId;
import com.skaotico.servicio.rest.usuarioRol.model.UsuarioRolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRolModel, UsuarioRolId> {
    List<UsuarioRolModel> findByUsuarioId(Long usuarioId);

}