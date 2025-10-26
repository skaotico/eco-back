package com.skaotico.servicio.rest.lectura.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.lectura.dto.LecturaArbolDTO;
import com.skaotico.servicio.rest.lectura.model.LecturaArbol;
import com.skaotico.servicio.rest.lectura.service.LecturaArbolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturas")
public class LecturaArbolController {

    private final LecturaArbolService service;

    public LecturaArbolController(LecturaArbolService service) {
        this.service = service;
    }

    // ================= Crear Lectura =================
    @PostMapping
    @Operation(summary = "Crear lectura de árbol",
            description = "Crea una nueva lectura asociada a un árbol a partir del DTO proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lectura creada exitosamente",
                    content = @Content(
                            schema = @Schema(implementation = LecturaArbol.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"arbolId\": 5,\n" +
                                            "  \"fecha\": \"2025-10-01T10:30:00\",\n" +
                                            "  \"valor\": 12.5\n" +
                                            "}"
                            )
                    )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Campo arbolId es obligatorio\" }"
                            )
                    ))
    })
    public ResponseEntity<ApiResponseGeneric<LecturaArbol>> crearLectura(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con los datos de la lectura a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LecturaArbolDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"arbolId\": 5,\n" +
                                            "  \"valor\": 12.5,\n" +
                                            "  \"fecha\": \"2025-10-01T10:30:00\"\n" +
                                            "}"
                            )
                    )
            )
            @RequestBody LecturaArbolDTO dto) {
        LecturaArbol lecturaCreada = service.crearLectura(dto);
        return ResponseEntity.ok(ResponseFactory.ok(lecturaCreada, "Lectura creada exitosamente"));
    }

    // ================= Listar Lecturas Paginadas =================
    @GetMapping
    @Operation(summary = "Listar todas las lecturas de árboles",
            description = "Devuelve todas las lecturas registradas paginadas")
    public ResponseEntity<ApiResponseGeneric<Page<LecturaArbol>>> listarLecturas(
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página", example = "50")
            @RequestParam(defaultValue = "50") int size) {
        Page<LecturaArbol> lecturas = service.obtenerTodas(page, size);
        return ResponseEntity.ok(ResponseFactory.ok(lecturas, "Lista de lecturas obtenida"));
    }

    // ================= Obtener Lectura por ID =================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener lectura por ID",
            description = "Devuelve los datos de una lectura específica por su ID")
    public ResponseEntity<ApiResponseGeneric<LecturaArbol>> obtenerPorId(
            @Parameter(description = "ID de la lectura a obtener", example = "1")
            @PathVariable Long id) {
        LecturaArbol lectura = service.obtenerPorId(id);
        return ResponseEntity.ok(ResponseFactory.ok(lectura, "Lectura obtenida correctamente"));
    }

    // ================= Obtener Lecturas por Árbol =================
    @GetMapping("/arbol/{arbolId}")
    @Operation(summary = "Obtener lecturas por árbol",
            description = "Devuelve todas las lecturas asociadas a un árbol específico")
    public ResponseEntity<ApiResponseGeneric<List<LecturaArbol>>> obtenerPorArbol(
            @Parameter(description = "ID del árbol", example = "5")
            @PathVariable Long arbolId) {
        List<LecturaArbol> lecturas = service.obtenerLecturasPorArbol(arbolId);
        return ResponseEntity.ok(ResponseFactory.ok(lecturas, "Lecturas obtenidas correctamente"));
    }
}
