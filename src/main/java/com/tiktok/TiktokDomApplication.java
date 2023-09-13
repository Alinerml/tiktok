package com.tiktok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableWebMvc //启用基于Java的Web应用程序中的Spring MVC
public class TiktokDomApplication {
    private static final Logger log = LoggerFactory.getLogger(com.tiktok.common.springbootwebsocketdemo.util.JsonUtil.class);

    public static void main(String[] args) {

        SpringApplication.run(TiktokDomApplication.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Tiktok is running! Access URLs:\n\t" +
                "Swagger文档: \thttp://localhost:8080/douyin/swagger-ui.html\n" +
                "----------------------------------------------------------");
    }

}
