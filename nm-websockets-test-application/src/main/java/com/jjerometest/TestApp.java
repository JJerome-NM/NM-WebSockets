package com.jjerometest;

import com.jjerome.reflection.context.anotation.EnableNMWebSockets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = "com.jjerometest"
)
@EnableNMWebSockets(
        enableSpringComponentScan = true,
        scanBasePackages = "com.jjerometest"
)
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
}
