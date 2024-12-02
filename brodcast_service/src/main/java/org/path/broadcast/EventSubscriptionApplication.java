package org.path.broadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.path.*"})
public class EventSubscriptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSubscriptionApplication.class, args);
    }
}
