package com.skaotico.servicio.rest.recinto.service.impl;

import com.skaotico.servicio.rest.recinto.dto.RecintoRequestDto;
import com.skaotico.servicio.rest.recinto.dto.RecintoResponseDTO;
import com.skaotico.servicio.rest.recinto.mapper.RecintoMapper;
import com.skaotico.servicio.rest.recinto.model.RecintoModel;
import com.skaotico.servicio.rest.recinto.repository.RecintoRepository;
import com.skaotico.servicio.rest.recinto.service.RecintoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecintoServiceImpl implements RecintoService {

    private final RecintoRepository recintoRepository;
    private final RecintoMapper recintoMapper;

    public RecintoServiceImpl(RecintoRepository recintoRepository, RecintoMapper recintoMapper) {
        this.recintoRepository = recintoRepository;
        this.recintoMapper = recintoMapper;
    }

    @Override
    public RecintoResponseDTO crearRecinto(RecintoRequestDto recintoDto) {
        RecintoModel recinto = recintoMapper.toModel(recintoDto);
        RecintoModel savedRecinto = recintoRepository.save(recinto);
        return recintoMapper.toResponse(savedRecinto);
    }

    @Override
    public RecintoResponseDTO obtenerRecintoPorId(Long id) {
        RecintoModel recinto = recintoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado con id: " + id));
        return recintoMapper.toResponse(recinto);
    }

    @Override
    public boolean eliminarRecinto(Long id) {
        if (recintoRepository.existsById(id)) {
            recintoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<RecintoResponseDTO> listarTodosRecintos() {
        return recintoRepository.findAll()
                .stream()
                .map(recintoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RecintoResponseDTO modificarRecinto(Long id, RecintoRequestDto dto) {
        RecintoModel recintoExistente = recintoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado con id: " + id));

        // Actualización condicional de campos
        if (dto.getNombre() != null) recintoExistente.setNombre(dto.getNombre());
        if (dto.getUbicacion() != null) recintoExistente.setUbicacion(dto.getUbicacion());
        if (dto.getCapacidadAreas() != null) recintoExistente.setCapacidadAreas(dto.getCapacidadAreas());
        if (dto.getDescripcion() != null) recintoExistente.setDescripcion(dto.getDescripcion());

        RecintoModel actualizado = recintoRepository.save(recintoExistente);
        return recintoMapper.toResponse(actualizado);
    }
}
