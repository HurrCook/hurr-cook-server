package com.github.hurrcook.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().components(new Components().addSecuritySchemes("JWT", securityScheme())).addSecurityItem(new SecurityRequirement().addList("JWT")).info(info());
    }

    private Info info() {

        return new Info()
                .title("HurrCook")
                .description("자취생 냉장고 레시피 추천 어플")
                .version("0.2.0");
    }

    private SecurityScheme securityScheme() {

        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }
}