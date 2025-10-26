package com.skaotico.servicio.rest.auth.controller;

import com.skaotico.servicio.rest.auth.dto.AuthResponseDto;
import com.skaotico.servicio.rest.auth.dto.LoginDto;
import com.skaotico.servicio.rest.auth.service.AuthService;
import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación para gestionar login y logout de usuarios.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ================= Login =================
    /**
     * Realiza el login de un usuario y genera un token JWT.
     *
     * @param loginDto DTO con email y contraseña.
     * @return ResponseEntity con token y datos del usuario o error 401.
     */
    @PostMapping("/login")
    @Operation(
            summary = "Login de usuario",
            description = "Recibe email y contraseña, y retorna un JWT si las credenciales son correctas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login exitoso",
                            content = @Content(
                                    schema = @Schema(implementation = AuthResponseDto.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\",\n" +
                                                    "  \"usuario\": {\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"nombre\": \"Juan\",\n" +
                                                    "    \"email\": \"usuario@ejemplo.com\",\n" +
                                                    "    \"roles\": [\"USER\"]\n" +
                                                    "  }\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales incorrectas",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = "{ \"error\": \"Usuario o contraseña incorrectos\" }"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseGeneric<AuthResponseDto>> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto authResponse = authService.login(loginDto);
        return ResponseEntity.ok(ResponseFactory.ok(authResponse, "Login exitoso"));
    }

    // ================= Logout =================
    /**
     * Realiza el logout de un usuario.
     *
     * @return ResponseEntity indicando logout exitoso.
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseGeneric<String>> logout() {
        return ResponseEntity.ok(ResponseFactory.ok("Logout exitoso", "Usuario deslogueado correctamente"));
    }
}
