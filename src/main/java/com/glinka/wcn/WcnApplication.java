package com.glinka.wcn;

import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcnApplication.class, args);
    }

}
