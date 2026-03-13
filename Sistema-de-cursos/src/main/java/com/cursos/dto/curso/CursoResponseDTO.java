package com.cursos.dto.curso;

import lombok.Data;

@Data
public class CursoResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
}
