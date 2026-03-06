package com.aegiscapital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class AegisCapitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(AegisCapitalApplication.class, args);
    }
}
