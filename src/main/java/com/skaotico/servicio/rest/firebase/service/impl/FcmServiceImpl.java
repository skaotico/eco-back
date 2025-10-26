package com.skaotico.servicio.rest.firebase.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.skaotico.servicio.rest.firebase.service.FcmService;
import org.springframework.stereotype.Service;

@Service
public class FcmServiceImpl implements FcmService {

    @Override
    public String enviarATodos(String titulo, String cuerpo) throws Exception {

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(cuerpo)
                        .build())
                .setTopic("todos")
                .build();

        // Enviamos la notificación y guardamos la respuesta
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Notificación enviada: " + response);

        return response;
    }
}
