package ru.home.discountparser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.home.discountparser.parser.ozon.dto.Ozon;
import ru.home.discountparser.service.TelegramService;
import ru.home.discountparser.service.MessageSender;

import java.io.File;
import java.util.List;

@Service
public class MessageSenderIml implements MessageSender {
    @Lazy
    @Autowired
    private TelegramService telegramService;

    public void prepareMessageForOzon(List<Ozon> availableOzonItemPosts) {
        availableOzonItemPosts.stream().filter(Ozon::isAvailable).forEach(ozon ->
                telegramService.sendMessageWithImage(
                        String.format("Товар появился в наличии %n%n %s", ozon.getProductUrl()), ozon.getScreenShot())
        );
    }

    public void prepareMessageWithTextAndImageUrl(String text, String imageUrl) {
        telegramService.sendTextWithImageUrl(text, imageUrl);
    }

    public void prepareMessageWithImageFile(String text, File imageFile) {
        telegramService.sendMessageWithImage(text, imageFile);
    }

    public void prepareMessageWithText(String text) {
        telegramService.sendTextMessage(text);
    }
}
