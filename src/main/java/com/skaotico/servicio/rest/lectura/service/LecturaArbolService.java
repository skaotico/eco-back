package com.skaotico.servicio.rest.lectura.service;



import com.skaotico.servicio.rest.lectura.dto.LecturaArbolDTO;
import com.skaotico.servicio.rest.lectura.model.LecturaArbol;
import org.springframework.data.domain.Page;
import java.util.List;

public interface LecturaArbolService {
    LecturaArbol crearLectura(LecturaArbolDTO dto);
    public Page<LecturaArbol> obtenerTodas(int page, int size);
    LecturaArbol obtenerPorId(Long id);
    List<LecturaArbol> obtenerLecturasPorArbol(Long arbolId);
}
