package ru.home.discountparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Главный класс приложения DiscountParserApplication, использующий Spring Boot и позволяющий запускать приложение.
 */
@SpringBootApplication
@EnableScheduling
public class DiscountParserApplication {

    /**
     * Главный метод приложения, который запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(DiscountParserApplication.class, args);
    }
}
