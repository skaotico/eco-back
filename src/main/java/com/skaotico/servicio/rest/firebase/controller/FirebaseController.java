package com.skaotico.servicio.rest.firebase.controller;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import com.skaotico.servicio.rest.firebase.service.FcmService;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class FirebaseController {

    private final FcmService firebaseService;

    public FirebaseController(FcmService firebaseService) {
        this.firebaseService = firebaseService;
    }

    // ================= Enviar Notificación a Todos =================
    @PostMapping("/enviar/todos")
    @Operation(
            summary = "Enviar notificación a todos los usuarios",
            description = "Envía una notificación push a todos los usuarios registrados en Firebase"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al enviar la notificación")
    })
    public ResponseEntity<ApiResponseGeneric<String>> enviarNotificacionATodos(
            @Parameter(description = "Título de la notificación", required = true)
            @RequestParam String titulo,
            @Parameter(description = "Cuerpo de la notificación", required = true)
            @RequestParam String cuerpo,
            HttpServletRequest request) {

        try {
            String resultado = firebaseService.enviarATodos(titulo, cuerpo);
            return ResponseEntity.ok(ResponseFactory.ok(resultado, "Notificación enviada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseFactory.error(
                            "Error al enviar la notificación: " + e.getMessage(),
                            request,
                            "INTERNAL_ERROR"
                    ));
        }
    }
}
