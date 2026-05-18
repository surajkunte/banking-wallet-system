package com.suraj.bank.authservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingAuthOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banking Wallet System - Auth Service")
                        .description("Authentication APIs for user registration, login, token refresh, and logout.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Suraj B Kunte")
                                .email("surajbk.ec21@bmsce.ac.in"))
                        .license(new License()
                                .name("MIT License")));
    }
}