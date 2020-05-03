package com.edgar.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class BBSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BBSApplication.class, args);
    }

}
