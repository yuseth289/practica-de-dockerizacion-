package com.cursos.controller;

import com.cursos.dto.curso.CursoRequestDTO;
import com.cursos.dto.curso.CursoResponseDTO;
import com.cursos.service.CursoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> crearCurso(@RequestBody CursoRequestDTO dto) {
        CursoResponseDTO response = cursoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        List<CursoResponseDTO> response = cursoService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtenerCurso(@PathVariable Long id) {
        try {
            CursoResponseDTO response = cursoService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizarCurso(
            @PathVariable Long id,
            @RequestBody CursoRequestDTO dto) {

        try {
            CursoResponseDTO actualizado = cursoService.actualizar(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        try {
            cursoService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}