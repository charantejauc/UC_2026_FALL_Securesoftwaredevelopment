package com.securesoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureDatabaseAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SecureDatabaseAccessApplication.class, args
        );
    }
}

