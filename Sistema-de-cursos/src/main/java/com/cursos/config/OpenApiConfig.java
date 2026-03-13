package com.cursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cursosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Cursos")
                        .description("Documentacion de la plataforma de cursos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Cursos")
                                .email("soporte@cursos.local"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Servidor local"));
    }
}
