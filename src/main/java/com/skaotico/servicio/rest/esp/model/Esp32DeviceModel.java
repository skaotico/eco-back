package com.skaotico.servicio.rest.esp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "esp32_device")
public class Esp32DeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mac", length = 17, nullable = false, unique = true)
    private String mac;

    @Column(name = "chip_id", length = 20, nullable = false, unique = true)
    private String chipId;

    @Column(name = "model", length = 50, nullable = false)
    private String model;

    @Column(name = "cores", nullable = false)
    private Short cores;

    @Column(name = "revision", nullable = false)
    private Short revision;

    @Column(name = "flash", nullable = false)
    private Long flash;

    @Column(name = "sdk", length = 20, nullable = false)
    private String sdk;

    @Column(name = "ip")
    private String ip;

    @Column(name = "rssi")
    private Short rssi;

    @Column(name = "uptime")
    private Long uptime;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn = LocalDateTime.now();


}
