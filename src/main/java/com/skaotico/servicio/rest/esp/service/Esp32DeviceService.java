package com.skaotico.servicio.rest.esp.service;



import com.skaotico.servicio.rest.esp.dto.Esp32DeviceRequestDto;
import com.skaotico.servicio.rest.esp.dto.Esp32DeviceResponseDto;

import java.util.List;

public interface Esp32DeviceService {
    Esp32DeviceResponseDto saveOrUpdate(Esp32DeviceRequestDto request);
    Esp32DeviceResponseDto getById(Long id);
    List<Esp32DeviceResponseDto> getAll();
    Esp32DeviceResponseDto update(Long id, Esp32DeviceRequestDto request);
    void delete(Long id);
}