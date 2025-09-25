package com.skaotico.servicio.rest;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Prueba {

    public static void main(String[] args) {
        // Fecha a validar
        String fechaNacimiento = "1987-01-24";

        // Patrón de fecha YYYY-MM-DD
        Pattern fechaPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = fechaPattern.matcher(fechaNacimiento);

        // Validación
        if (matcher.matches()) {
            System.out.println("La fecha es válida: " + fechaNacimiento);
        } else {
            System.out.println("La fecha NO es válida: " + fechaNacimiento);
        }
    }
}
