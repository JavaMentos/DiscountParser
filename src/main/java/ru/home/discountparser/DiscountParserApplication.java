package ru.home.discountparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.home.discountparser.telegram.properties.TelegramProperties;

/**
 * Главный класс приложения DiscountParserApplication, использующий Spring Boot и позволяющий запускать приложение.
 */
@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories
@EnableConfigurationProperties(TelegramProperties.class)
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
