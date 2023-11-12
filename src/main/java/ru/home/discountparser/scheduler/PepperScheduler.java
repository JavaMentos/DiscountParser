package ru.home.discountparser.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.parser.pepper.PepperParserPost;

/**
 * Класс PepperScheduler отвечает за проверку новых публикаций на Pepper
 * и отправку уведомлений о новых публикациях через сервис Telegram.
 */
@Component
@Log4j2
@AllArgsConstructor
public class PepperScheduler {

    private final PepperParserPost pepperParserPost;

    /**
     * Запускает проверку новых публикаций на Pepper с заданным интервалом из конфигурации.
     * Отправляет уведомления о новых публикациях в Telegram.
     */
    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void checkNewPostsFromPepper() {
        pepperParserPost.checkNewPosts();
    }
}
