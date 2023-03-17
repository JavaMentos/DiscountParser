package ru.home.discountparser.telegram;

public interface TelegramService {
    void sendMessageTextAndPhoto(String text, String imageUrl);

}
