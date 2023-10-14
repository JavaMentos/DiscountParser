package ru.home.discountparser.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.ozon.dto.Ozon;
import ru.home.discountparser.telegram.service.MessageSender;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static ru.home.discountparser.ozon.OzonListContainer.ozonProducts;

/**
 * Класс SchedulerOzon отвечает за проверку доступности товаров на Ozon
 * и отправку уведомлений о доступности товаров через сервис Telegram.
 */
@Component
@Log4j2
@AllArgsConstructor
public class OzonScheduler {

    private final  OzonParser ozonParser;
    private final MessageSender telegramMessageSender;

    /**
     * Запускает проверку доступности товаров на Ozon каждые 5 минут с случайной задержкой до 2 минут.
     * При наличии доступных товаров, отправляет уведомления в Telegram.
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void runCheckAvailableGoodsFromOzon() {
        // генерируем случайную задержку для натруальной имитации пользователя
        int randomDelay = new Random().nextInt(30);
        try {
            TimeUnit.SECONDS.sleep(randomDelay);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }

        ozonParser.checkAvailabilityOfGoods();
        telegramMessageSender.prepareMessageForOzon(ozonProducts);
        removeProcessedOzonItems();
    }


    /**
     * Удаляет товары, доступность которых уже была проверена и уведомления отправлены.
     */
    private void removeProcessedOzonItems() {
        ozonProducts.removeIf(Ozon::isAvailable);
    }
}
