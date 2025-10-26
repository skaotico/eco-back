package com.skaotico.servicio.rest.arbol.controller;

import com.skaotico.servicio.rest.arbol.dto.ArbolCreateDto;
import com.skaotico.servicio.rest.arbol.dto.ArbolResponseDto;
import com.skaotico.servicio.rest.arbol.dto.ImagenResponseDto;
import com.skaotico.servicio.rest.arbol.model.ArbolModel;
import com.skaotico.servicio.rest.arbol.service.ArbolService;
import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.storage.minio.MinioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/arbol")
public class ArbolController {

    private final ArbolService arbolService;
    private final MinioService minioService;

    public ArbolController(ArbolService arbolService, MinioService minioService) {
        this.arbolService = arbolService;
        this.minioService = minioService;
    }


    @PostMapping(value = "/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Subir imagen de árbol",
            description = "Permite subir una imagen asociada a un árbol y devuelve la información de la imagen guardada",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Imagen subida exitosamente",
                            content = @Content(
                                    schema = @Schema(implementation = ImagenResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se recibió ningún archivo",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseGeneric.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al subir la imagen",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseGeneric.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseGeneric<ImagenResponseDto>> subirImagen(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseFactory.error("No se recibió ningún archivo", request, "BAD_REQUEST"));
        }

        try {
            ImagenResponseDto imagen = arbolService.guardarImagen(file);
            return ResponseEntity.ok(ResponseFactory.ok(imagen, "Imagen subida exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseFactory.error("Error al subir la imagen: " + e.getMessage(), request, "INTERNAL_ERROR"));
        }
    }



    @Operation(
            summary = "Crear un árbol",
            description = "Crea un nuevo árbol con los datos proporcionados en el DTO"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Árbol creado exitosamente",
                    content = @Content(
                            schema = @Schema(implementation = ArbolResponseDto.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"especie\": \"Pino\",\n" +
                                            "  \"altura\": 12.5,\n" +
                                            "  \"fechaPlantacion\": \"2025-10-01\",\n" +
                                            "  \"imagenUrl\": \"http://ejemplo.com/imagen.png\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseGeneric.class),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Campo especie es obligatorio\" }"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponseGeneric<ArbolResponseDto>> crear(
            @RequestBody ArbolCreateDto arbol,
            @AuthenticationPrincipal Jwt jwt) {

        ArbolResponseDto creado = arbolService.crear(arbol, jwt);
        return ResponseEntity.ok(ResponseFactory.ok(creado, "Árbol creado exitosamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<Void>> eliminar(
            @PathVariable Long id,
            HttpServletRequest request) {

        boolean eliminado = arbolService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(ResponseFactory.ok(null, "Árbol eliminado exitosamente"));
        } else {
            return ResponseEntity.status(404)
                    .body(ResponseFactory.error("Árbol no encontrado", request, "NOT_FOUND"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<ArbolResponseDto>> buscarPorId(
            @PathVariable Long id,
            HttpServletRequest request) {

        return arbolService.buscarPorId(id)
                .map(arbol -> ResponseEntity.ok(ResponseFactory.ok(arbol, "Árbol encontrado")))
                .orElse(ResponseEntity.status(404)
                        .body(ResponseFactory.error("Árbol no encontrado", request, "NOT_FOUND")));
    }

    @GetMapping
    public ResponseEntity<ApiResponseGeneric<List<ArbolResponseDto>>> listarTodos(
            @AuthenticationPrincipal Jwt jwt) {

        List<ArbolResponseDto> lista = arbolService.listarTodos(jwt);
        return ResponseEntity.ok(ResponseFactory.ok(lista, "Lista de árboles obtenida"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseGeneric<List<ArbolModel>>> listarPorNombre(
            @RequestParam String especie) {

        List<ArbolModel> lista = arbolService.listarPorNombre(especie);
        return ResponseEntity.ok(ResponseFactory.ok(lista, "Lista filtrada por especie"));
    }

    @GetMapping("/imagen/{fileName}")
    public ResponseEntity<ApiResponseGeneric<byte[]>> getImagen(
            @PathVariable String fileName,
            HttpServletRequest request) {

        try {
            byte[] bytes = minioService.getFileBytes("arbol-images", fileName);
            return ResponseEntity.ok(ResponseFactory.ok(bytes, "Imagen obtenida"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseFactory.error("Imagen no encontrada: " + fileName, request, "NOT_FOUND"));
        }
    }
}
