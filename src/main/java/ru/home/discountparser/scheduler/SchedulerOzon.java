package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

import java.util.List;

@Component
public class SchedulerOzon {
    @Autowired
    private OzonParser ozonParser;
    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    @Scheduled(initialDelayString = "${schedule.ozon.init}", fixedDelayString = "${schedule.ozon.work}")
    public void runCheckAvailableGoodsFromOzon() {
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
