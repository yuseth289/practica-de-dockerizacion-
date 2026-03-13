package com.cursos.service;

import com.cursos.dto.curso.CursoRequestDTO;
import com.cursos.dto.curso.CursoResponseDTO;
import com.cursos.entity.Categoria;
import com.cursos.entity.Curso;
import com.cursos.repository.CategoriaRepository;
import com.cursos.repository.CursoRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;

    public CursoService(CursoRepository cursoRepository,
                        CategoriaRepository categoriaRepository) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CursoResponseDTO crear(CursoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        Curso curso = new Curso();
        curso.setTitulo(dto.getTitulo());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCategoria(categoria);

        Curso guardado = cursoRepository.save(curso);

        return convertirAResponse(guardado);
    }

    public List<CursoResponseDTO> listar() {
        return cursoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        return convertirAResponse(curso);
    }

    @Transactional
    public CursoResponseDTO actualizar(Long id, CursoRequestDTO dto) {

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        curso.setTitulo(dto.getTitulo());
      curso.setDescripcion(dto.getDescripcion());
      curso.setCategoria(categoria);

        Curso actualizado = cursoRepository.save(curso);

        return convertirAResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {

        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("El curso no existe");
        }

        cursoRepository.deleteById(id);
    }

    private CursoResponseDTO convertirAResponse(Curso curso) {

        CursoResponseDTO dto = new CursoResponseDTO();
        dto.setId(curso.getId());
        dto.setTitulo(curso.getTitulo());
        dto.setDescripcion(curso.getDescripcion());
        dto.setCategoriaNombre(curso.getCategoria().getNombre());

        return dto;
    }
}