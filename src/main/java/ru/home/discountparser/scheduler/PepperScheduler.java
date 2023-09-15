package ru.home.discountparser.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.pepper.dto.Pepper;
import ru.home.discountparser.telegram.TelegramService;
import ru.home.discountparser.telegram.message.TelegramMessageSender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.home.discountparser.pepper.PepperListContainer.currentPepperPosts;
import static ru.home.discountparser.pepper.PepperListContainer.newPepperPosts;

/**
 * Класс PepperScheduler отвечает за проверку новых публикаций на Pepper
 * и отправку уведомлений о новых публикациях через сервис Telegram.
 */
@Component
@Log4j2
@AllArgsConstructor
public class PepperScheduler {

    private final TelegramMessageSender telegramMessageSender;

    private final PepperParser pepperParser;

    /**
     * Запускает проверку новых публикаций на Pepper с заданным интервалом из конфигурации.
     * Отправляет уведомления о новых публикациях в Telegram.
     */
    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void checkNewPostsFromPepper() {
        log.info(LocalDateTime.now() + " Стартовал планировщик по пеппер");
        pepperParser.checkNewPosts();
        currentPepperPosts.addAll(newPepperPosts);
        sendMessagesForNewPepperPosts(currentPepperPosts);
        currentPepperPosts.forEach(pepperPost -> pepperPost.setIsNew(false));
    }

    /**
     * Удаляет устаревшие объекты публикаций на Pepper каждые 12 часов.
     */
    @Scheduled(cron = "0 */12 * * * *")
    public void removeYesterdayPosts() {
        log.info("Стартовал планировщик и очистил список постов, current "
                + currentPepperPosts.size() + " new " + newPepperPosts.size());
        LocalDate yesterday = LocalDate.now().minusDays(1);
        currentPepperPosts.removeIf(pepperPost -> pepperPost.getDate().equals(yesterday));
        newPepperPosts.clear();
    }

    /**
     * Отправляет сообщения через сервис Telegram о новых публикациях на Pepper.
     *
     * @param pepperPosts список объектов Pepper для проверки новых публикаций и отправки уведомлений
     */
    private void sendMessagesForNewPepperPosts(List<Pepper> pepperPosts) {
        pepperPosts.stream()
                .filter(Pepper::isNew)
                .forEach(pepperPost -> telegramMessageSender.sendTextWithImageUrl(
                        pepperParser.formatPepperPostMessage(pepperPost), pepperPost.getImageUrl())
                );
    }
}
