package com.nextjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
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
