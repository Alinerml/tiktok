package com.tiktok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc //启用基于Java的Web应用程序中的Spring MVC
@ComponentScan(basePackages = {"com.tiktok", "com.tiktok.common.**"})
public class TiktokDomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiktokDomApplication.class, args);
    }

}
