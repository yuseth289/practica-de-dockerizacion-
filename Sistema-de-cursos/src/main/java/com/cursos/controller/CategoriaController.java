package com.cursos.controller;


import com.cursos.dto.categoria.CategoriaRequestDTO;
import com.cursos.dto.categoria.CategoriaResponseDTO;
import com.cursos.service.CategoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaRequestDTO dto) {
        try {
            CategoriaResponseDTO response = categoriaService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(obtenerStatusDeValidacion(e))
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> response = categoriaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        try {
            CategoriaResponseDTO response = categoriaService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO dto) {

        try {
            CategoriaResponseDTO actualizado = categoriaService.actualizar(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(obtenerStatusDeValidacion(e))
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private HttpStatus obtenerStatusDeValidacion(IllegalArgumentException e) {
        if (e.getMessage() != null && e.getMessage().contains("existe")) {
            return HttpStatus.CONFLICT;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
