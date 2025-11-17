package com.main.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Configuration
public class BaseProjectSwaggerConfiguration {
    private static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.app.url}")
    private String serverAppUrl;

    /**
     * Open API Configuration Bean
     *
     * @return
     */
    @Bean
    public OpenAPI openApiConfiguration(
    ) {
        return new OpenAPI()
                    .addServersItem(new Server().url(serverAppUrl + contextPath))
                    .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                    .components(
                            new Components()
                                    .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                            new SecurityScheme()
                                                    .name(SECURITY_SCHEME_NAME)
                                                    .type(SecurityScheme.Type.HTTP)
                                                    .scheme("bearer")
                                                    .bearerFormat("JWT")
                                    )
                    );
    }

    /**
     * Contact details for the developer(s)
     *
     * @return
     */
    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("support@xxx.vn");
        contact.setName("xxx");
        contact.setUrl("xxxx");
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }

    /**
     * License creation
     *
     * @return
     */
    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0");
        license.setExtensions(Collections.emptyMap());
        return license;
    }
}
