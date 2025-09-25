package com.skaotico.servicio.rest.lectura.controller;


import com.skaotico.servicio.rest.lectura.dto.LecturaArbolDTO;
import com.skaotico.servicio.rest.lectura.model.LecturaArbol;
import com.skaotico.servicio.rest.lectura.service.LecturaArbolService;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@RestController
@RequestMapping("/api/lecturas")
public class LecturaArbolController {

    private final LecturaArbolService service;

    public LecturaArbolController(LecturaArbolService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LecturaArbol> crearLectura(@RequestBody LecturaArbolDTO dto) {
        LecturaArbol lectura = service.crearLectura(dto);
        return ResponseEntity.ok(lectura);
    }

    @GetMapping
    public ResponseEntity<Page<LecturaArbol>> listarLecturas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Page<LecturaArbol> lecturas = service.obtenerTodas(page, size);
        return ResponseEntity.ok(lecturas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LecturaArbol> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/arbol/{arbolId}")
    public ResponseEntity<List<LecturaArbol>> obtenerPorArbol(@PathVariable Long arbolId) {
        List<LecturaArbol> lecturas = service.obtenerLecturasPorArbol(arbolId);
        if (lecturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lecturas);
    }
}
