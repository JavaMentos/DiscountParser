package ru.home.discountparser.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.pepper.dto.Pepper;
import ru.home.discountparser.telegram.message.MessageSender;

import java.time.LocalDate;

import static ru.home.discountparser.pepper.PepperListContainer.currentPepperPosts;

/**
 * Класс PepperScheduler отвечает за проверку новых публикаций на Pepper
 * и отправку уведомлений о новых публикациях через сервис Telegram.
 */
@Component
@Log4j2
@AllArgsConstructor
public class PepperScheduler {

    private final MessageSender telegramMessageSender;

    private final PepperParser pepperParser;

    /**
     * Запускает проверку новых публикаций на Pepper с заданным интервалом из конфигурации.
     * Отправляет уведомления о новых публикациях в Telegram.
     */
    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void checkNewPostsFromPepper() {
                pepperParser.checkNewPosts();
        sendMessagesForNewPepperPosts();
        currentPepperPosts.forEach(pepperPost -> pepperPost.setNew(false));
    }

    /**
     * Удаляет устаревшие объекты публикаций на Pepper каждые 12 часов.
     */
    @Scheduled(cron = "0 0 */12 * * *")
    public void removeYesterdayPosts() {
        if (!currentPepperPosts.isEmpty()) {
            log.info("Стартовал планировщик и очистил список постов, current "
                    + currentPepperPosts.size());
            LocalDate yesterday = LocalDate.now().minusDays(1);
            currentPepperPosts.removeIf(pepperPost -> pepperPost.getDate().equals(yesterday));
        }
    }

    /**
     * Отправляет сообщения через сервис Telegram о новых публикациях на Pepper.
     */
    private void sendMessagesForNewPepperPosts() {
        currentPepperPosts.stream()
                .filter(Pepper::isNew)
                .forEach(pepperPost -> telegramMessageSender.prepareMessageWithTextAndImageUrl(
                        pepperParser.formatPepperPostMessage(pepperPost), pepperPost.getImageUrl())
                );
    }
}
