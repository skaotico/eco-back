package com.skaotico.servicio.rest.area.controller;

import com.skaotico.servicio.rest.area.dto.AreaDto;
import com.skaotico.servicio.rest.area.dto.CreateAreaDTO;
import com.skaotico.servicio.rest.area.service.AreaService;
import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Tag(name = "Áreas", description = "API para la gestión de áreas")
public class AreaController {

    private final AreaService areaService;

    // ================= Crear Área =================
    @Operation(summary = "Crear un área", description = "Crea una nueva área con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponseGeneric<CreateAreaDTO>> createArea(@RequestBody CreateAreaDTO createAreaDTO) {

        return ResponseEntity.ok(ResponseFactory.ok(areaService.createArea(createAreaDTO), "Área creada correctamente"));
    }

    // ================= Obtener Área por ID =================
    @Operation(summary = "Obtener un área por ID", description = "Devuelve los datos de un área específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<CreateAreaDTO>> getArea(
            @Parameter(description = "ID del área a obtener") @PathVariable Long id) {
        CreateAreaDTO area = areaService.getAreaById(id);
        return ResponseEntity.ok(ResponseFactory.ok(area, "Área obtenida correctamente"));
    }

    // ================= Listar Todas las Áreas =================
    @Operation(summary = "Listar todas las áreas", description = "Devuelve todas las áreas registradas")
    @GetMapping
    public ResponseEntity<ApiResponseGeneric<List<CreateAreaDTO>>> getAllAreas() {
        List<CreateAreaDTO> areas = areaService.getAllAreas();
        return ResponseEntity.ok(ResponseFactory.ok(areas, "Lista de áreas obtenida correctamente"));
    }

    // ================= Actualizar Área =================
    @Operation(summary = "Actualizar un área", description = "Actualiza los datos de un área existente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<CreateAreaDTO>> updateArea(
            @Parameter(description = "ID del área a actualizar") @PathVariable Long id,
            @RequestBody AreaDto areaDto) {
        CreateAreaDTO areaActualizada = areaService.updateArea(id, areaDto);
        return ResponseEntity.ok(ResponseFactory.ok(areaActualizada, "Área actualizada correctamente"));
    }

    // ================= Eliminar Área =================
    @Operation(summary = "Eliminar un área", description = "Elimina un área existente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<Void>> deleteArea(
            @Parameter(description = "ID del área a eliminar") @PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.ok(ResponseFactory.ok(null, "Área eliminada correctamente"));
    }

    @GetMapping("/recinto/{recintoId}")
    public List<CreateAreaDTO> listarPorRecinto(@PathVariable Long recintoId) {
        return areaService.findByRecintoId(recintoId);
    }
}
