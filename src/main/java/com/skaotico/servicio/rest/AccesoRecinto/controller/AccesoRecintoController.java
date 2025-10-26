package com.skaotico.servicio.rest.accesoRecinto.controller;

import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoRequestDto;
import com.skaotico.servicio.rest.accesoRecinto.dto.AccesoRecintoResponseDto;
import com.skaotico.servicio.rest.accesoRecinto.service.AccesoRecintoService;
import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
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
    public ResponseEntity<ApiResponseGeneric<AccesoRecintoResponseDto>> crear(@RequestBody AccesoRecintoRequestDto request) {
        return ResponseEntity.ok(ResponseFactory.ok(service.guardar(request), " "));

    }

    @GetMapping("/{usuarioId}/{recintoId}")
    public ResponseEntity<ApiResponseGeneric<AccesoRecintoResponseDto>> obtener(
            @PathVariable Long usuarioId,
            @PathVariable Long recintoId) {
        return ResponseEntity.ok(ResponseFactory.ok(service.buscarPorId(usuarioId, recintoId), " "));


    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponseGeneric<List<AccesoRecintoResponseDto>>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ResponseFactory.ok(service.listarPorUsuario(usuarioId), " "));

    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<ApiResponseGeneric<List<AccesoRecintoResponseDto>>> listarPorRecinto(@PathVariable Long recintoId) {
        return ResponseEntity.ok(ResponseFactory.ok(service.listarPorRecinto(recintoId), " "));

    }

    @DeleteMapping("/{usuarioId}/{recintoId}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long usuarioId,
            @PathVariable Long recintoId) {

        service.eliminar(usuarioId, recintoId);
        return ResponseEntity.noContent().build();
    }
}
