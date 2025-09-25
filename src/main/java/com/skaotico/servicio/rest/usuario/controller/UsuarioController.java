package com.skaotico.servicio.rest.usuario.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para la gestión de Usuarios.
 *
 * <p>Proporciona endpoints para crear, consultar, listar usuarios y subir imágenes.</p>
 *
 * <p>Todos los endpoints retornan {@link ApiResponseGeneric} para estandarizar las respuestas.</p>
 */
@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> create(@Valid @RequestBody UsuarioCreateDTO usuarioDto) {
        Usuario usuarioCreado = usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.status(201)
                .body(new ApiResponseGeneric<>(true, usuarioCreado, "Usuario creado correctamente"));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<List<Usuario>>> findAll() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(new ApiResponseGeneric<>(true, usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> findOne(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(new ApiResponseGeneric<>(true, usuario, "Usuario encontrado"));
    }

    @PostMapping(value = "/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir imagen de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida correctamente"),
            @ApiResponse(responseCode = "400", description = "Archivo vacío"),
            @ApiResponse(responseCode = "500", description = "Error al subir imagen")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> subirImagen(
            @AuthenticationPrincipal Jwt jwt,
            @RequestPart(name = "file", required = true) MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseGeneric<>(false, null, "No se recibió ningún archivo"));
        }

        try {
            Usuario usuario = usuarioService.guardarImagenUsuario(file, jwt);
            return ResponseEntity.ok(new ApiResponseGeneric<>(true, usuario, "Usuario modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponseGeneric<>(false, null, "Ocurrió un error al subir la imagen: " + e.getMessage()));
        }
    }

    @GetMapping("/actual")
    @Operation(summary = "Obtener el usuario actual según el JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> getUsuarioActual(@AuthenticationPrincipal Jwt jwt) {

        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(jwt);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseGeneric<>(false, null, "Usuario no encontrado"));
        }

        return ResponseEntity.ok(new ApiResponseGeneric<>(true, usuario, "Usuario encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
