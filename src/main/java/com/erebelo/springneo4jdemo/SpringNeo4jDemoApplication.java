package com.erebelo.springneo4jdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringNeo4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNeo4jDemoApplication.class, args);
    }
}
