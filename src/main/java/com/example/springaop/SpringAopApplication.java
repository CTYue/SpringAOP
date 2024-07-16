package com.example.springaop;

import com.example.springaop.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAopApplication {

    public static void main(String[] args) {
        TestService.print();
        SpringApplication.run(SpringAopApplication.class, args);
    }

}
