package ru.home.discountparser.telegram.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.home.discountparser.ozon.dto.Ozon;
import ru.home.discountparser.telegram.TelegramService;
import ru.home.discountparser.telegram.message.TelegramMessageSender;

import java.io.File;
import java.util.List;

@Service
public class TelegramMessageSenderIml implements TelegramMessageSender {
    @Lazy
    @Autowired
    private TelegramService telegramService;

    @Override
    public void sendMessagesForOzon(List<Ozon> availableOzonItemPosts) {
        telegramService.sendMessagesForOzon(availableOzonItemPosts);
    }

    @Override
    public void sendTextWithImageUrl(String text, String imageUrl) {
        telegramService.sendTextWithImageUrl(text, imageUrl);
    }

    @Override
    public void sendTextWithImageFile(String text, File imageFile) {
        telegramService.sendTextWithImageFile(text, imageFile);
    }

    @Override
    public void sendTextMessage(String text) {
        telegramService.sendTextMessage(text);
    }
}
