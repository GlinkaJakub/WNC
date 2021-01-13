package com.glinka.wcn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket get(){
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UsernamePasswordAuthenticationToken.class)
                .select()
                .paths(PathSelectors.regex ( "^(?!/(error).*$).*$" ))
                .build()
                .apiInfo(createApiInfo())
                .securitySchemes(Collections.singletonList(createSchema()))
                .securityContexts(Collections.singletonList(createContext()));
    }

    private SecurityContext createContext(){
        return SecurityContext.builder()
                .securityReferences(createRef())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> createRef(){
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything"
        );
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
    }

    private SecurityScheme createSchema(){
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Wyszykiwarka Czasopism Naukowych API",
                "Wyszykiwarka Czasopism Nauowych API",
                "version 1.0.0",
                "http://url.pl",
                new Contact("User", "http://url.pl", "my@mail.pl"),
                "my license",
                "http://license.url.pl",
                Collections.emptyList()
        );
    }
}
