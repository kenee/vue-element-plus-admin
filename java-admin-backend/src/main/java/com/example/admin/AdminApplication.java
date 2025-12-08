package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口类
 *
 * @author example
 */
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("Java Admin Backend started successfully!");
        System.out.println("API base path: http://localhost:3000/api");
        System.out.println("Swagger: http://localhost:3000/api/swagger-ui.html");
    }

}