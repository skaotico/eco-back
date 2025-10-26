package com.skaotico.servicio.rest.accesoRecinto.repository;

import com.skaotico.servicio.rest.accesoRecinto.model.AccesoRecintoId;
import com.skaotico.servicio.rest.accesoRecinto.model.AccesoRecintoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccesoRecintoRepository extends JpaRepository<AccesoRecintoModel, AccesoRecintoId> {

    List<AccesoRecintoModel> findByUsuarioId(Long usuarioId);

    List<AccesoRecintoModel> findByRecintoId(Long recintoId);
}
