package com.tiktok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc //启用基于Java的Web应用程序中的Spring MVC
public class TiktokDomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiktokDomApplication.class, args);
    }

}
