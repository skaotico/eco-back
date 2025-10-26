package com.skaotico.servicio.rest.usuario.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de Usuarios.
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
    public ResponseEntity<ApiResponseGeneric<Usuario>> create(
            @Valid @RequestBody UsuarioCreateDTO usuarioDto,
            HttpServletRequest request) {

        try {
            Usuario usuarioCreado = usuarioService.crearUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseFactory.ok(usuarioCreado, "Usuario creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseFactory.error("Error al crear usuario: " + e.getMessage(), request, "CREATE_USER_ERROR"));
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<List<Usuario>>> findAll(HttpServletRequest request) {
        try {
            List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
            return ResponseEntity.ok(ResponseFactory.ok(usuarios, "Usuarios obtenidos correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseFactory.error("Error al obtener usuarios: " + e.getMessage(), request, "LIST_USERS_ERROR"));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> findOne(@PathVariable Long id, HttpServletRequest request) {


            return ResponseEntity.ok(ResponseFactory.ok(usuarioService.obtenerUsuarioPorId(id), "Usuario encontrado"));

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
            @RequestPart(name = "file", required = true) MultipartFile file,
            HttpServletRequest request) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseFactory.error("No se recibió ningún archivo", request, "FILE_EMPTY"));
        }

        try {
            Usuario usuario = usuarioService.guardarImagenUsuario(file, jwt);
            return ResponseEntity.ok(ResponseFactory.ok(usuario, "Imagen subida correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseFactory.error("Ocurrió un error al subir la imagen: " + e.getMessage(), request, "UPLOAD_IMAGE_ERROR"));
        }
    }

    @GetMapping("/actual")
    @Operation(summary = "Obtener el usuario actual según el JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<Usuario>> getUsuarioActual(
            @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(jwt);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseFactory.error("Usuario no encontrado", request, "USER_NOT_FOUND"));
        }

        return ResponseEntity.ok(ResponseFactory.ok(usuario, "Usuario actual obtenido correctamente"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseGeneric<String>> eliminarUsuario(@PathVariable Long id, HttpServletRequest request) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok(ResponseFactory.ok("Usuario eliminado correctamente", "Operación exitosa"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseFactory.error(e.getMessage(), request, "DELETE_USER_ERROR"));
        }
    }
}
