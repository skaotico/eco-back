package com.skaotico.servicio.rest.esp.dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class Esp32DeviceResponseDto {
    private Long id;
    private String mac;
    private String chipId;
    private String model;
    private Short cores;
    private Short revision;
    private Long flash;
    private String sdk;
    private String ip;
    private Short rssi;
    private Long uptime;
    private LocalDateTime lastSeen;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}