package com.skaotico.servicio.rest.lectura.repository;


import com.skaotico.servicio.rest.lectura.model.LecturaArbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturaArbolRepository extends JpaRepository<LecturaArbol, Long> {
    List<LecturaArbol> findByArbolId(Long arbolId);
}
