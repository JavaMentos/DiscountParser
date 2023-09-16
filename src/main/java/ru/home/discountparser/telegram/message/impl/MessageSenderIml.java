package ru.home.discountparser.telegram.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.home.discountparser.ozon.dto.Ozon;
import ru.home.discountparser.telegram.TelegramService;
import ru.home.discountparser.telegram.message.MessageSender;

import java.io.File;
import java.util.List;

@Service
public class MessageSenderIml implements MessageSender {
    @Lazy
    @Autowired
    private TelegramService telegramService;

    @Override
    public void prepareMessageForOzon(List<Ozon> availableOzonItemPosts) {
        availableOzonItemPosts.stream().filter(Ozon::isAvailable).forEach(ozon ->
                telegramService.sendMessageWithImage(
                        String.format("Товар появился в наличии %n%n %s", ozon.getProductUrl()), ozon.getScreenShot())
        );
    }

    @Override
    public void prepareMessageWithTextAndImageUrl(String text, String imageUrl) {
        telegramService.sendTextWithImageUrl(text, imageUrl);
    }

    @Override
    public void prepareMessageWithAndImageFile(String text, File imageFile) {
        telegramService.sendMessageWithImage(text, imageFile);
    }

    @Override
    public void prepareMessageWithText(String text) {
        telegramService.sendTextMessage(text);
    }
}
