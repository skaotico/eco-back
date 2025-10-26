package com.skaotico.servicio.rest.area.service.impl;

import com.skaotico.servicio.rest.area.dto.AreaDto;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;
import com.skaotico.servicio.rest.area.mapper.AreaMapper;
import com.skaotico.servicio.rest.area.model.Area;
import com.skaotico.servicio.rest.area.model.TipoAreaEnum;
import com.skaotico.servicio.rest.area.repository.AreaRepository;
import com.skaotico.servicio.rest.area.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;


    @Override
    public CreateAreaDTO createArea(CreateAreaDTO createAreaDTO) {
       Area area = areaMapper.toModel(createAreaDTO);
       Area saved = areaRepository.save(area);
        return areaMapper.toDto(saved);
    }

    @Override
    public CreateAreaDTO getAreaById(Long id) {
        com.skaotico.servicio.rest.area.model.Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id " + id));
        return areaMapper.toDto(area);
    }

    @Override
    public List<CreateAreaDTO> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(areaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CreateAreaDTO updateArea(Long id, AreaDto areaDTO) {
        com.skaotico.servicio.rest.area.model.Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id " + id));

        area.setNombre(areaDTO.getNombre());
        area.setRecintoId(areaDTO.getRecintoId());
        area.setSuperficieM2(areaDTO.getSuperficieM2());
        if (areaDTO.getTipoArea() != null) {
            area.setTipoArea(TipoAreaEnum.valueOf(areaDTO.getTipoArea()));
        }

        com.skaotico.servicio.rest.area.model.Area updated = areaRepository.save(area);
        return areaMapper.toDto(area);
    }

    @Override
    public void deleteArea(Long id) {
        areaRepository.deleteById(id);
    }

    @Override
    public List<Area> obtenerAreasPorRecinto(List<Long> recintoIds) {
        return areaRepository.findByRecintoIdIn(recintoIds);
    }

    @Override
    public List<CreateAreaDTO> findByRecintoId(Long recintoId) {
        return  areaMapper.toDtoList(areaRepository.findByRecintoId(recintoId)) ;
    }


}
