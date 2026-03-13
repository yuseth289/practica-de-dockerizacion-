package com.cursos.dto.estudiante;

import lombok.Data;
import java.util.List;

@Data
public class EstudianteRequestDTO {

    private String nombre;
    private String email;
    private List<Long> cursosIds; 
}