package com.skaotico.servicio.rest.nacionalidad.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.nacionalidad.dto.NacionalidadDto;
import com.skaotico.servicio.rest.nacionalidad.service.NacionalidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nacionalidades")
public class NacionalidadController {

    private final NacionalidadService nacionalidadService;

    public NacionalidadController(NacionalidadService nacionalidadService) {
        this.nacionalidadService = nacionalidadService;
    }

    /**
     * Endpoint para listar todas las nacionalidades.
     *
     * @return Lista de Nacionalidad DTOs
     */
    @Operation(summary = "Listar nacionalidades", description = "Obtiene todas las nacionalidades disponibles, filtradas por especie si aplica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponseGeneric<List<NacionalidadDto>>> listarNacionalidades() {
        List<NacionalidadDto> lista = nacionalidadService.listarNacionalidades();
        return ResponseEntity.ok(ResponseFactory.ok(lista, "Lista de nacionalidades obtenida correctamente"));
    }

    /**
     * Endpoint para insertar nacionalidades de forma masiva.
     *
     * @param nacionalidades Lista de nacionalidades a insertar
     * @return Lista de nacionalidades insertadas
     */
    @Operation(summary = "Insertar nacionalidades masivas", description = "Permite insertar varias nacionalidades en un solo request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nacionalidades insertadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/masivas")
    public ResponseEntity<ApiResponseGeneric<List<NacionalidadDto>>> insertarMasivas(
            @RequestBody List<NacionalidadDto> nacionalidades) {
        List<NacionalidadDto> insertadas = nacionalidadService.insertarNacionalidadesMasivas(nacionalidades);
        return ResponseEntity.ok(ResponseFactory.ok(insertadas, "Nacionalidades insertadas correctamente"));
    }
}
