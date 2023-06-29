package com.jjerome;

import com.jjerome.anotation.EnableNMWebSockets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = "com.jjerome"
)
@EnableNMWebSockets(
        enableSpringComponentScan = true,
        scanBasePackages = "com.jjerome"
)
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
}
