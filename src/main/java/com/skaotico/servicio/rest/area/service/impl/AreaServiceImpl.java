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
    public AreaDto createArea(CreateAreaDTO createAreaDTO) {
        com.skaotico.servicio.rest.area.model.Area area = areaMapper.toModel(createAreaDTO);
        com.skaotico.servicio.rest.area.model.Area saved = areaRepository.save(area);
        return mapToDTO(saved);
    }

    @Override
    public AreaDto getAreaById(Long id) {
        com.skaotico.servicio.rest.area.model.Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id " + id));
        return mapToDTO(area);
    }

    @Override
    public List<AreaDto> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AreaDto updateArea(Long id, AreaDto areaDTO) {
        com.skaotico.servicio.rest.area.model.Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id " + id));

        area.setNombre(areaDTO.getNombre());
        area.setRecintoId(areaDTO.getRecintoId());
        area.setSuperficieM2(areaDTO.getSuperficieM2());
        if (areaDTO.getTipoArea() != null) {
            area.setTipoArea(TipoAreaEnum.valueOf(areaDTO.getTipoArea()));
        }

        com.skaotico.servicio.rest.area.model.Area updated = areaRepository.save(area);
        return mapToDTO(updated);
    }

    @Override
    public void deleteArea(Long id) {
        areaRepository.deleteById(id);
    }

    @Override
    public List<Area> obtenerAreasPorRecinto(List<Long> recintoIds) {
        return areaRepository.findByRecintoIdIn(recintoIds);
    }

    private AreaDto mapToDTO(com.skaotico.servicio.rest.area.model.Area area) {
        return AreaDto.builder()
                .id(area.getId())
                .nombre(area.getNombre())
                .recintoId(area.getRecintoId())
                .superficieM2(area.getSuperficieM2())
                .tipoArea(area.getTipoArea() != null ? area.getTipoArea().name() : null)
                .build();
    }

    private com.skaotico.servicio.rest.area.model.Area mapToEntity(AreaDto dto) {
        return com.skaotico.servicio.rest.area.model.Area.builder()
                .nombre(dto.getNombre())
                .recintoId(dto.getRecintoId())
                .superficieM2(dto.getSuperficieM2())
                .tipoArea(dto.getTipoArea() != null ? TipoAreaEnum.valueOf(dto.getTipoArea()) : null)
                .build();
    }
}
