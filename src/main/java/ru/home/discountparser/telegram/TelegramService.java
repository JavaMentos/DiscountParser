package ru.home.discountparser.telegram;

import java.io.File;

public interface TelegramService {
    void sendMessageTextAndPhoto(String text, String imageUrl);
    void sendMessageTextAndPhoto(String text, File file);
    void sendMessageText(String text);
}
