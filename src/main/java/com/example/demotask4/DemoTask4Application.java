package com.example.demotask4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoTask4Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoTask4Application.class, args);
    }

}
