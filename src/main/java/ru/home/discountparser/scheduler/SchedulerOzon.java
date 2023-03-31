package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class SchedulerOzon {
    @Autowired
    private OzonParser ozonParser;
    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    //Эта cron expression будет запускать задачу каждые 10 минут, начиная с 0 минуты каждого часа.
    @Scheduled(cron = "0 */10 * * * ?")
    public void runCheckAvailableGoodsFromOzon() {
        int randomDelay = new Random().nextInt(2); // генерируем случайную задержку от 5 до 10 минут
        System.out.println("Scheduler is running with delay of " + randomDelay + " minutes");
        try {
            TimeUnit.MINUTES.sleep(randomDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ozonParser.checkAvailabilityOfGoods();
        sendMessageWithOzon(ozonParser.listOzons);
        removeAbandonedOzon();
    }

    private void sendMessageWithOzon(List<Ozon> listOzon) {
        listOzon.stream().filter(Ozon::isGoodsAvailable).forEach(ozon ->
                telegramService.sendMessageTextAndPhoto(
                        String.format("Товар появился в наличии \n\n %s",ozon.getUrlGoods()), ozon.getScreenShot())
        );
    }

    private void removeAbandonedOzon(){
        OzonParser.listOzons.removeIf(Ozon::isGoodsAvailable);
    }
}
