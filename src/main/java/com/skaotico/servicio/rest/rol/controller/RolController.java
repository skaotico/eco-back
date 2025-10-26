package com.skaotico.servicio.rest.rol.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;

import com.skaotico.servicio.rest.rol.dto.RolRequestDto;
import com.skaotico.servicio.rest.rol.dto.RolResponseDto;

import com.skaotico.servicio.rest.rol.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rol")
@Tag(name = "Rol", description = "API para gestión de Roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/listar")
    @Operation(summary = "Obtener todos los roles")
    public ResponseEntity<ApiResponseGeneric<List<RolResponseDto>>> listarRoles() {
        return ResponseEntity.ok(ResponseFactory.ok(this.rolService.obtenerTodosLosRoles(), "Roles obtenidos correctamente"));

    }

    @PostMapping
    @Operation(summary = "Crear un nuevo rol")
    public ResponseEntity<ApiResponseGeneric<RolResponseDto>> crearRol(@Valid @RequestBody RolRequestDto rolRequestDto) {
        return ResponseEntity.ok(ResponseFactory.ok(rolService.crearRol(rolRequestDto), "Rol creado correctamente"));
    }

    @Operation(summary = "Obtener un rol por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<RolResponseDto>> obtenerRolPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseFactory.ok(rolService.obtenerRolPorId(id), "Rol creado correctamente"));

    }

    @Operation(summary = "Obtener un rol por su nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponseGeneric<RolResponseDto>> obtenerRolPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(ResponseFactory.ok(rolService.obtenerRolPorNombre(nombre), " "));


    }

    @Operation(summary = "Eliminar un rol por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<Boolean>> eliminarRol(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseFactory.ok(rolService.eliminarRol(id) , " "));

    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualiza un rol existente",
            description = "Actualiza el nombre y la descripción de un rol según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<RolResponseDto>> actualizarRol(@PathVariable Long id, @RequestBody @Valid RolRequestDto rolRequestDto) {
        return ResponseEntity.ok(ResponseFactory.ok(rolService.actualizarRol(id, rolRequestDto) , " "));

    }
}