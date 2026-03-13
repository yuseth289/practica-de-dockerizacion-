package com.cursos.service;

import com.cursos.dto.estudiante.EstudianteRequestDTO;
import com.cursos.dto.estudiante.EstudianteResponseDTO;
import com.cursos.entity.Curso;
import com.cursos.entity.Estudiante;
import com.cursos.repository.CursoRepository;
import com.cursos.repository.EstudianteRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public EstudianteService(EstudianteRepository estudianteRepository,
                             CursoRepository cursoRepository) {

        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public EstudianteResponseDTO registrar(EstudianteRequestDTO dto) {

        estudianteRepository.findByEmail(dto.getEmail())
                .ifPresent(e -> {
                    throw new RuntimeException("El email ya está registrado");
                });

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setEmail(dto.getEmail());

        Set<Curso> cursos = dto.getCursosIds().stream()
                .map(idCurso -> cursoRepository.findById(idCurso)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + idCurso)))
                .collect(Collectors.toSet());

        estudiante.setCursos(cursos);

        Estudiante guardado = estudianteRepository.save(estudiante);

        return convertirAResponse(guardado);
    }

    public List<EstudianteResponseDTO> listar() {

        return estudianteRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public EstudianteResponseDTO buscarPorId(Long id) {

        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        return convertirAResponse(estudiante);
    }

    @Transactional
    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {

        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        estudiante.setNombre(dto.getNombre());
        estudiante.setEmail(dto.getEmail());

        Set<Curso> cursos = dto.getCursosIds().stream()
                .map(idCurso -> cursoRepository.findById(idCurso)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + idCurso)))
                .collect(Collectors.toSet());

        estudiante.setCursos(cursos);

        Estudiante actualizado = estudianteRepository.save(estudiante);

        return convertirAResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {

        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("El estudiante no existe");
        }

        estudianteRepository.deleteById(id);
    }

    @Transactional
    public EstudianteResponseDTO inscribirEnCurso(Long estudianteId, Long cursoId) {

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        estudiante.getCursos().add(curso); // Set evita duplicados
        Estudiante actualizado = estudianteRepository.save(estudiante);

        return convertirAResponse(actualizado);
    }

    private EstudianteResponseDTO convertirAResponse(Estudiante estudiante) {

        EstudianteResponseDTO dto = new EstudianteResponseDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre());
        dto.setEmail(estudiante.getEmail());

        List<String> nombresCursos = estudiante.getCursos().stream()
                .map(Curso::getTitulo)
                .collect(Collectors.toList());

        dto.setCursos(nombresCursos);

        return dto;
    }
}
