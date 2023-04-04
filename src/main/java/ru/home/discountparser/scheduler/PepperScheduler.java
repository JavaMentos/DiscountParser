package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.home.discountparser.pepper.Pepper;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.telegram.TelegramService;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс PepperScheduler отвечает за проверку новых публикаций на Pepper
 * и отправку уведомлений о новых публикациях через сервис Telegram.
 */
@Component
public class PepperScheduler {
    @Autowired
    @Lazy
    private TelegramService telegramService;
    @Autowired
    private PepperParser pepperParser;

    /**
     * Запускает проверку новых публикаций на Pepper с заданным интервалом из конфигурации.
     * Отправляет уведомления о новых публикациях в Telegram.
     */
    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void checkNewPostsFromPepper() {
        pepperParser.checkNewPosts();
        PepperParser.currentPepperPosts.addAll(PepperParser.newPepperPosts);
        sendMessagesForNewPepperPosts(PepperParser.currentPepperPosts);
        PepperParser.currentPepperPosts.forEach(pepperPost -> pepperPost.setIsNew(false));
    }

    /**
     * Удаляет устаревшие объекты публикаций на Pepper каждые 12 часов.
     */
    @Scheduled(cron = "0 */12 * * * *")
    public void removeYesterdayPosts() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        PepperParser.currentPepperPosts.removeIf(pepperPost -> pepperPost.getDate().equals(yesterday));
    }

    /**
     * Отправляет сообщения через сервис Telegram о новых публикациях на Pepper.
     *
     * @param pepperPosts список объектов Pepper для проверки новых публикаций и отправки уведомлений
     */
    private void sendMessagesForNewPepperPosts(List<Pepper> pepperPosts) {
        pepperPosts.stream()
                .filter(Pepper::isNew)
                .forEach(pepperPost -> telegramService.sendTextWithImageUrl(
                        pepperParser.formatPepperPostMessage(pepperPost), pepperPost.getImageUrl())
                );
    }
}
