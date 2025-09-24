package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"org.example", "contactmanager"})
public class ContactManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactManagementApplication.class, args);
    }
}
