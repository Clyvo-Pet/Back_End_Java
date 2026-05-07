package br.com.fiap.ClyvoPet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clyvo Pet API")
                        .description("Sistema de Gestão para Clínica Veterinária — FIAP ADS 3° Semestre 2026")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Enrico Delesporte | Felippe Modesto | Vitor Dias")
                                .email("contato@clyvopet.com.br")));
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            operation.getParameters().removeIf(p -> p.getName().equals("pageable"));
            return operation;
        };
    }
}
