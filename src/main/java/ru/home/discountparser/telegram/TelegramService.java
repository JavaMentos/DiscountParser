package ru.home.discountparser.telegram;

import ru.home.discountparser.ozon.dto.Ozon;

import java.io.File;
import java.util.List;

/**
 * Интерфейс TelegramService предоставляет методы для отправки сообщений через Telegram-бота.
 */
public interface TelegramService {

    /**
     * Возвращает имя бота.
     *
     * @return имя бота
     */
    String getBotUsername();

    /**
     * Отправляет сообщения через сервис Telegram о наличии товаров на Ozon.
     *
     * @param availableOzonItemPosts список объектов Ozon для проверки наличия и отправки уведомлений
     */
//    void sendMessagesForOzon(List<Ozon> availableOzonItemPosts);

    /**
     * Отправляет текстовое сообщение и изображение по URL.
     *
     * @param text     текст сообщения
     * @param imageUrl URL изображения
     */
    void sendTextWithImageUrl(String text, String imageUrl);

    /**
     * Отправляет текстовое сообщение и изображение в виде файла.
     *
     * @param text     текст сообщения
     * @param imageFile файл изображения
     */
    void sendMessageWithImage(String text, File imageFile);

    /**
     * Отправляет текстовое сообщение.
     *
     * @param text текст сообщения
     */
    void sendTextMessage(String text);
}
