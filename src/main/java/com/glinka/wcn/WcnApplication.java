package com.glinka.wcn;

import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
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
        return new ApiInfo("Wyszykiwarka Czasopism Nauowych API",
                "Wyszykiwarka Czasopism Nauowych API",
                "verionn 1.0.0",
                "http://url.pl",
                new Contact("User", "http://url.pl", "my@mail.pl"),
                "mi licenese",
                "http://license.url.pl",
                Collections.emptyList()
                );
    }
}
