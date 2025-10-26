package com.skaotico.servicio.rest.usuarioRol.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolRequestDto;
import com.skaotico.servicio.rest.usuarioRol.dto.UsuarioRolResponseDto;
import com.skaotico.servicio.rest.usuarioRol.service.UsuarioRolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-rol")
public class UsuarioRolController {

    private final UsuarioRolService service;

    public UsuarioRolController(UsuarioRolService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponseGeneric<UsuarioRolResponseDto>> create(@RequestBody UsuarioRolRequestDto dto) {
        return ResponseEntity.ok(ResponseFactory.ok(service.create(dto), "" ));

    }

    @GetMapping
    public ResponseEntity<ApiResponseGeneric<List<UsuarioRolResponseDto>>> getAll() {
        return ResponseEntity.ok(ResponseFactory.ok(service.getAll(), "" ));

    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<ApiResponseGeneric<List<UsuarioRolResponseDto>>> getRolPorUsuarioID(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ResponseFactory.ok(service.getRolPorUsuarioID(usuarioId), "" ));

    }

    @DeleteMapping("/{usuarioId}/{rolId}")
    public ResponseEntity<ApiResponseGeneric<Boolean>> delete(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        return ResponseEntity.ok(ResponseFactory.ok(service.delete(usuarioId, rolId), "" ));

    }
}