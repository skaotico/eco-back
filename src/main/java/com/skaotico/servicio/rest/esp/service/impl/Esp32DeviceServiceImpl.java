package com.skaotico.servicio.rest.esp.service.impl;



import com.skaotico.servicio.rest.esp.dto.Esp32DeviceRequestDto;
import com.skaotico.servicio.rest.esp.dto.Esp32DeviceResponseDto;
import com.skaotico.servicio.rest.esp.mapper.Esp32DeviceMapper;
import com.skaotico.servicio.rest.esp.model.Esp32DeviceModel;
import com.skaotico.servicio.rest.esp.repository.Esp32DeviceRepository;
import com.skaotico.servicio.rest.esp.service.Esp32DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Esp32DeviceServiceImpl implements Esp32DeviceService {

    private final Esp32DeviceRepository repository;
    private final Esp32DeviceMapper mapper;

    public Esp32DeviceServiceImpl(Esp32DeviceRepository repository, Esp32DeviceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Esp32DeviceResponseDto saveOrUpdate(Esp32DeviceRequestDto request) {
        Esp32DeviceModel device = mapper.toModel(request);

        // Buscar por chip_id
        Optional<Esp32DeviceModel> existingDeviceOpt = repository.findByChipId(device.getChipId());

        if (existingDeviceOpt.isPresent()) {

            Esp32DeviceModel existingDevice = existingDeviceOpt.get();
            existingDevice.setActualizadoEn(LocalDateTime.now());

            Esp32DeviceModel updated = repository.save(existingDevice);
            return mapper.toDto(updated);
        } else {
            // Crear nuevo
            device.setCreadoEn(LocalDateTime.now());
            device.setActualizadoEn(LocalDateTime.now());
            Esp32DeviceModel saved = repository.save(device);
            return mapper.toDto(saved);
        }
    }


    @Override
    public Esp32DeviceResponseDto getById(Long id) {
        Esp32DeviceModel device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ESP32 Device no encontrado"));
        return mapper.toDto(device);
    }

    @Override
    public List<Esp32DeviceResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Esp32DeviceResponseDto update(Long id, Esp32DeviceRequestDto request) {
        Esp32DeviceModel device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ESP32 Device no encontrado"));
        mapper.updateModelFromDto(request, device);
        device.setActualizadoEn(LocalDateTime.now());
        Esp32DeviceModel updated = repository.save(device);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        Esp32DeviceModel device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ESP32 Device no encontrado"));
        repository.delete(device);
    }
}