package ru.home.discountparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiscountParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscountParserApplication.class, args);
    }
}
