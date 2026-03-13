CREATE DATABASE IF NOT EXISTS plataforma_cursos
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE plataforma_cursos;

CREATE TABLE IF NOT EXISTS categoria (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS curso (
  id BIGINT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(255) NOT NULL,
  descripcion VARCHAR(255) NULL,
  categoria_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  KEY idx_curso_categoria_id (categoria_id),
  CONSTRAINT fk_curso_categoria
    FOREIGN KEY (categoria_id)
    REFERENCES categoria (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS estudiante (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_estudiante_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS estudiantes_cursos (
  estudiante_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  PRIMARY KEY (estudiante_id, curso_id),
  KEY idx_estudiantes_cursos_curso (curso_id),
  CONSTRAINT fk_estudiantes_cursos_estudiante
    FOREIGN KEY (estudiante_id)
    REFERENCES estudiante (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_estudiantes_cursos_curso
    FOREIGN KEY (curso_id)
    REFERENCES curso (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB;
