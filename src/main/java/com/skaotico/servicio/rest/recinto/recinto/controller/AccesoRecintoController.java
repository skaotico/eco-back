package com.skaotico.servicio.rest.recinto.recinto.controller;

import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.recinto.recinto.dto.AccesoRecintoResponseDto;
import com.skaotico.servicio.rest.recinto.recinto.service.AccesoRecintoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accesos")
@RequiredArgsConstructor
public class AccesoRecintoController {

    private final AccesoRecintoService service;

    @PostMapping
    public ResponseEntity<AccesoRecintoResponseDto> crear(@RequestBody AccesoRecintoRequestDto request) {
        return ResponseEntity.ok(service.guardar(request));
    }

    @GetMapping("/{usuarioId}/{recintoId}")
    public ResponseEntity<AccesoRecintoResponseDto> obtener(
            @PathVariable Long usuarioId,
            @PathVariable Long recintoId) {

        return service.buscarPorId(usuarioId, recintoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AccesoRecintoResponseDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<AccesoRecintoResponseDto>> listarPorRecinto(@PathVariable Long recintoId) {
        return ResponseEntity.ok(service.listarPorRecinto(recintoId));
    }

    @DeleteMapping("/{usuarioId}/{recintoId}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long usuarioId,
            @PathVariable Long recintoId) {

        service.eliminar(usuarioId, recintoId);
        return ResponseEntity.noContent().build();
    }
}
