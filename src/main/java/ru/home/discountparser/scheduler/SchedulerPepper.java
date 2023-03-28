package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.pepper.Pepper;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Component
public class SchedulerPepper {
    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;
    @Autowired
    private PepperParser pr;


    //    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void runCheckNewPostFromPepper() {
        pr.checkNewPosts();
        PepperParser.currentListPeppers.addAll(PepperParser.newListPeppers);
        sendMessageWithPepper(PepperParser.currentListPeppers);
        PepperParser.currentListPeppers.forEach(pepper -> pepper.setNewPost(false));
    }

    //    @Scheduled(cron = "0 */12 * * * *")
    public void removeYesterdayObjects() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        PepperParser.currentListPeppers.removeIf(pepper -> pepper.getDate().equals(yesterday));
    }

    private void sendMessageWithPepper(List<Pepper> listPepper) {
        listPepper.stream().filter(Pepper::isNewPost).forEach(pepper ->
                telegramService.sendMessageTextAndPhoto(pr.prettyStringPepperPosts(pepper)
                        , pepper.getImageUrl())
        );
    }
}
