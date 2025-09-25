package com.skaotico.servicio.rest.lectura.service.impl;


import com.skaotico.servicio.rest.firebase.service.FcmService;
import com.skaotico.servicio.rest.lectura.dto.LecturaArbolDTO;
import com.skaotico.servicio.rest.lectura.model.LecturaArbol;
import com.skaotico.servicio.rest.lectura.repository.LecturaArbolRepository;
import com.skaotico.servicio.rest.lectura.service.LecturaArbolService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LecturaArbolServiceImpl implements LecturaArbolService {

    private final LecturaArbolRepository repository;
    private final FcmService fcmService;

    public LecturaArbolServiceImpl(LecturaArbolRepository repository, FcmService fcmService) {
        this.repository = repository;
        this.fcmService = fcmService;
    }


    @Override
    public LecturaArbol crearLectura(LecturaArbolDTO dto) {
        LecturaArbol lectura = new LecturaArbol();
        lectura.setArbolId(dto.getArbolId());
        lectura.setTutorId(dto.getTutorId());
        lectura.setHumedad(dto.getHumedad());

        lectura.setIniciaRiego(dto.getIniciaRiego() != null ? dto.getIniciaRiego() : false);

        lectura.setCreadoEn(LocalDateTime.now());
        lectura.setActualizadoEn(LocalDateTime.now());
/*if(dto.getHumedad().intValue()  >20){
    try
    {
        fcmService.enviarATodos("regando"+dto.getArbolId(),"arbol regado con exito, ncl de humedad actual "+dto.getHumedad()+" %");
    }
    catch (Exception error){
        return null;
    }

}*/

        return repository.save(lectura);
    }

    @Override
    public Page<LecturaArbol> obtenerTodas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
    @Override

    public LecturaArbol obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public List<LecturaArbol> obtenerLecturasPorArbol(Long arbolId) {
        return repository.findByArbolId(arbolId);
    }
}
