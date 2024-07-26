package com.beehyv.broadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.beehyv.*"})
public class EventSubscriptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSubscriptionApplication.class, args);
    }
}
