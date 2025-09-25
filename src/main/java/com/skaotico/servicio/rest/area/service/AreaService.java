package com.skaotico.servicio.rest.area.service;

import com.skaotico.servicio.rest.area.dto.AreaDto;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;
import com.skaotico.servicio.rest.area.model.Area;

import java.util.List;

public interface AreaService {

    AreaDto createArea(CreateAreaDTO createAreaDTO);

    AreaDto getAreaById(Long id);

    List<AreaDto> getAllAreas();

    AreaDto updateArea(Long id, AreaDto areaDto);

    void deleteArea(Long id);

    List<Area> obtenerAreasPorRecinto(List<Long> recintoIds);


}
