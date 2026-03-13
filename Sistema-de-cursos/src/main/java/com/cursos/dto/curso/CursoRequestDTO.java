package com.cursos.dto.curso;

import lombok.Data;

@Data
public class CursoRequestDTO {

    private String titulo;
    private String descripcion;
    private Long categoriaId; 
}