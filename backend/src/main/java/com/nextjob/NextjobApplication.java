package com.nextjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NextjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextjobApplication.class, args);
    }

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello Nuxt from Spring Boot!";
    }
}
