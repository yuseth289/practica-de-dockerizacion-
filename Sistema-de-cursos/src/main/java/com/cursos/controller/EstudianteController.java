package com.cursos.controller;

import com.cursos.dto.estudiante.EstudianteRequestDTO;
import com.cursos.dto.estudiante.EstudianteResponseDTO;
import com.cursos.service.EstudianteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> registrarEstudiante(@RequestBody EstudianteRequestDTO dto) {
        EstudianteResponseDTO response = estudianteService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> listarEstudiantes() {
        List<EstudianteResponseDTO> response = estudianteService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> obtenerEstudiante(@PathVariable Long id) {
        try {
            EstudianteResponseDTO response = estudianteService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> actualizarEstudiante(
            @PathVariable Long id,
            @RequestBody EstudianteRequestDTO dto) {

        try {
            EstudianteResponseDTO actualizado = estudianteService.actualizar(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        try {
            estudianteService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{idEstudiante}/cursos/{idCurso}")
    public ResponseEntity<EstudianteResponseDTO> inscribirEnCurso(
            @PathVariable Long idEstudiante,
            @PathVariable Long idCurso) {

        try {
            EstudianteResponseDTO response = estudianteService.inscribirEnCurso(idEstudiante, idCurso);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
