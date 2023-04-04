package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramService;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Класс SchedulerOzon отвечает за проверку доступности товаров на Ozon
 * и отправку уведомлений о доступности товаров через сервис Telegram.
 */
@Component
public class OzonScheduler {
    @Autowired
    private OzonParser ozonParser;
    @Autowired
    @Lazy
    private TelegramService telegramService;

    /**
     * Запускает проверку доступности товаров на Ozon каждые 5 минут с случайной задержкой до 2 минут.
     * При наличии доступных товаров, отправляет уведомления в Telegram.
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void runCheckAvailableGoodsFromOzon() {
        int randomDelay = new Random().nextInt(2); // генерируем случайную задержку
        System.out.println("Scheduler is running with delay of " + randomDelay + " minutes");
        try {
            TimeUnit.MINUTES.sleep(randomDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ozonParser.checkAvailabilityOfGoods();
        sendMessagesForAvailableGoods(ozonParser.ozonProducts);
        removeProcessedOzonItems();
    }

    /**
     * Отправляет сообщения через сервис Telegram о наличии товаров на Ozon.
     *
     * @param availableOzonItemPosts список объектов Ozon для проверки наличия и отправки уведомлений
     */
    private void sendMessagesForAvailableGoods(List<Ozon> availableOzonItemPosts) {
        availableOzonItemPosts.stream().filter(Ozon::isAvailable).forEach(ozon ->
                telegramService.sendTextWithImageFile(
                        String.format("Товар появился в наличии \n\n %s", ozon.getProductUrl()), ozon.getScreenShot())
        );
    }

    /**
     * Удаляет товары, доступность которых уже была проверена и уведомления отправлены.
     */
    private void removeProcessedOzonItems(){
        OzonParser.ozonProducts.removeIf(Ozon::isAvailable);
    }
}
