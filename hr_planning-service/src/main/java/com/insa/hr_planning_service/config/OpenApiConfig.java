package com.insa.hr_planning_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "INSA",
                        email = "contact@insa.gov.et",
                        url = "https://insa.gov.et/"
                ),
                description = "OpenApi documentation for Hr_Planning service",
                title = "OpenApi Specification - INSA",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8187"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://insa.gov.et/"
                )
        },
        security = {
                @SecurityRequirement (
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme (
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
