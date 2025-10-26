package com.skaotico.servicio.rest.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseGeneric<T> {
    private boolean success;
    private T data;
    private String message;
    private Instant timestamp;
    private String path;
    private String errorCode;
}
