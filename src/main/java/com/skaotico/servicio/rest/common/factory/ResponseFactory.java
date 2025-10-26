package com.skaotico.servicio.rest.common.factory;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public class ResponseFactory {


    public static <T> ApiResponseGeneric<T> ok(T data, String message) {
        ApiResponseGeneric<T> response = new ApiResponseGeneric<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        return response;
    }


    public static <T> ApiResponseGeneric<T> error(String message, HttpServletRequest request, String errorCode) {
        ApiResponseGeneric<T> response = new ApiResponseGeneric<>();
        response.setSuccess(false);
        response.setData(null);
        response.setMessage(message);
        response.setTimestamp(Instant.now());
        response.setPath(request.getRequestURI());
        response.setErrorCode(errorCode);
        return response;
    }

    public static Object error(String rolNoEncontrado) {
        return null;
    }
}
