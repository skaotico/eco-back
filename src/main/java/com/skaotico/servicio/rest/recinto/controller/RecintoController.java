package com.skaotico.servicio.rest.recinto.controller;


import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.recinto.dto.RecintoRequestDto;
import com.skaotico.servicio.rest.recinto.dto.RecintoResponseDTO;
import com.skaotico.servicio.rest.recinto.service.impl.RecintoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/recinto")
@Tag(name = "Recintos", description = "API para gestión de recintos")
public class RecintoController {

   @Autowired
    private RecintoServiceImpl recintoServiceImpl;


    @PostMapping
    @Operation(summary = "Crear un nuevo recinto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recinto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ApiResponseGeneric<RecintoResponseDTO>> create(@Valid @RequestBody RecintoRequestDto recintoRequestDto) {
        return ResponseEntity.ok(ResponseFactory.ok(this.recintoServiceImpl.crearRecinto(recintoRequestDto), ""));
    }


    @GetMapping
    @Operation(summary = "Obtener todos los recintos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recintos obtenida correctamente")
    })
    public ResponseEntity<ApiResponseGeneric<List<RecintoResponseDTO>>> findAll() {
        return ResponseEntity.ok(ResponseFactory.ok(recintoServiceImpl.listarTodosRecintos(), ""));

    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener un recinto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recinto encontrado"),
            @ApiResponse(responseCode = "404", description = "Recinto no encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<RecintoResponseDTO>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseFactory.ok(recintoServiceImpl.obtenerRecintoPorId(id), ""));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un recinto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recinto Eliminado"),
            @ApiResponse(responseCode = "404", description = "Recinto no encontrado")
    })
    public ResponseEntity<ApiResponseGeneric<Boolean>> eliminarRecinto(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseFactory.ok(recintoServiceImpl.eliminarRecinto(id), ""));

    }
}
