package com.challenge.investimentos.investimentos_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI para documentação automática da API de Investimentos.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura as informações principais da documentação Swagger/OpenAPI.
     *
     * @return OpenAPI com título, descrição e versão da API.
     */
    @Bean
    public OpenAPI apiInfo() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Insira o token JWT obtido através do endpoint /auth/login. Formato: 'Bearer {token}'")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .info(new Info()
                .title("Investaê - API de Gestão de Investimentos")
                .description("API REST para gerenciamento de investimentos com autenticação JWT e controle de acesso baseado em roles (USER/ADMIN). " +
                           "Para usar a API, primeiro faça login em /auth/login e use o token retornado nos endpoints protegidos.")
                .version("v1.0.0"));
    }
}