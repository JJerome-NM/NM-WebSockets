package com.jjerome_test;

import com.jjerome.context.anotation.EnableNMWebSockets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = "com.jjerome_test"
)
@EnableNMWebSockets(
        enableSpringComponentScan = true,
        scanBasePackages = "com.jjerome_test"
)
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
}
