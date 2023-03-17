package ru.home.discountparser.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import ru.home.discountparser.pepper.Pepper;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

@Component
public class SchedulerPepper{
    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    @Autowired
    private PepperParser pr;

    @Scheduled(initialDelayString = "${schedule.pepper.init}", fixedDelayString = "${schedule.pepper.work}")
    public void runCheckNewPosts(){
        pr.checkNewPosts();
        PepperParser.currentListPeppers.addAll(PepperParser.newListPeppers);
        sendMessage();
        PepperParser.currentListPeppers.forEach(pepper -> pepper.setNewPost(false));


    }

    private void sendMessage(){
        PepperParser.currentListPeppers.stream().filter(Pepper::isNewPost).forEach(pepper ->
                telegramService.sendMessageTextAndPhoto(pr.prettyStringPepperPosts(pepper)
                        , pepper.getImageUrl())
        );
    }
}
