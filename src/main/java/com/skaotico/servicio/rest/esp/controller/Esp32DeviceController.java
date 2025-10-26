package com.skaotico.servicio.rest.esp.controller;

import com.skaotico.servicio.rest.esp.dto.Esp32DeviceRequestDto;
import com.skaotico.servicio.rest.esp.dto.Esp32DeviceResponseDto;
import com.skaotico.servicio.rest.esp.service.Esp32DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/esp32")
public class Esp32DeviceController {

    private final Esp32DeviceService service;

    public Esp32DeviceController(Esp32DeviceService service) {
        this.service = service;
    }

    // Crear un ESP32
    @PostMapping
    public ResponseEntity<Esp32DeviceResponseDto> create(@RequestBody Esp32DeviceRequestDto request) {
        Esp32DeviceResponseDto response = service.saveOrUpdate(request);
        return ResponseEntity.ok(response);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Esp32DeviceResponseDto> getById(@PathVariable Long id) {
        Esp32DeviceResponseDto response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Esp32DeviceResponseDto>> getAll() {
        List<Esp32DeviceResponseDto> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Esp32DeviceResponseDto> update(
            @PathVariable Long id,
            @RequestBody Esp32DeviceRequestDto request) {
        Esp32DeviceResponseDto response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}