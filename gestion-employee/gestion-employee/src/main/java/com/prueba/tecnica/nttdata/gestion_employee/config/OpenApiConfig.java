package com.prueba.tecnica.nttdata.gestion_employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    
    String securitySchema= "BearerAuth";

    @Bean 
    public OpenAPI config(){
        return new OpenAPI().info(new Info()
                        .title("Gestion del Personal por Oficinas")
                        .version("1.0")
                        .description("Endpoints para optimizar y gestionar los personales dentro de una empresa")
                        .contact(new Contact()
                                .name("Enrike Rod Keler Samaniego Guzman")
                                .email("erksg.10.26@gmail.com")
                                .url("https://rksamaniego.github.io/profile_enrike.dev/")        
                        )
                        .license(new License()
                                .name("licencia open code")
                                .url("https://opensource.org/licenses/MIT")
                        ))
                        .addSecurityItem(new SecurityRequirement().addList(securitySchema))
                        .components(new Components().addSecuritySchemes(securitySchema,
                                 new SecurityScheme()
                                 .name(securitySchema)
                                 .type(SecurityScheme.Type.HTTP)
                                 .scheme("Bearer")
                                 .bearerFormat("JWT")));
    }
}
