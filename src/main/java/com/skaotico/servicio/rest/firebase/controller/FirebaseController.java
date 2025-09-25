package com.skaotico.servicio.rest.firebase.controller;

import com.skaotico.servicio.rest.firebase.service.FcmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class FirebaseController {

    private final FcmService firebaseService;

    public FirebaseController(FcmService firebaseService) {
        this.firebaseService = firebaseService;
    }

    // Endpoint para enviar notificación a todos
    @PostMapping("/enviar/todos")
    public ResponseEntity<String> enviarNotificacionATodos(
            @RequestParam String titulo,
            @RequestParam String cuerpo) {
        try {
            String response = firebaseService.enviarATodos(titulo, cuerpo);
            return ResponseEntity.ok("Notificación enviada con ID: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error enviando notificación: " + e.getMessage());
        }
    }
}
