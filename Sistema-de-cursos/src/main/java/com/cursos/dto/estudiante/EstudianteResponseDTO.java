package com.cursos.dto.estudiante;

import lombok.Data;
import java.util.List;

@Data
public class EstudianteResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private List<String> cursos; 
}