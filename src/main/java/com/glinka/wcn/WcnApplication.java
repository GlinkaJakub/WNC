package com.glinka.wcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class WcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcnApplication.class, args);
    }

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
