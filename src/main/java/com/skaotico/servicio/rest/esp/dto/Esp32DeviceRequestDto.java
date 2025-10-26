package com.skaotico.servicio.rest.esp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Esp32DeviceRequestDto {
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
}