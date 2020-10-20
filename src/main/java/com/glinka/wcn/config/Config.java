package com.glinka.wcn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class Config {

    @Bean
    public Docket get(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(createApiInfo());
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
