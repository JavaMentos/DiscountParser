package ru.home.discountparser.telegram.service;

import ru.home.discountparser.ozon.dto.Ozon;

import java.io.File;
import java.util.List;

public interface MessageSender {

    /**
     * Подготавливает сообщение для отправки через Telegram о наличии товаров на Ozon.
     *
     * @param availableOzonItemPosts список объектов Ozon для проверки наличия и отправки уведомлений
     */
    void prepareMessageForOzon(List<Ozon> availableOzonItemPosts);

    /**
     * Подготавливает текстовое сообщение и изображение по URL для отправки через Telegram.
     *
     * @param text     текст сообщения
     * @param imageUrl URL изображения
     */
    void prepareMessageWithTextAndImageUrl(String text, String imageUrl);

    /**
     * Подготавливает текстовое сообщение и изображение в виде файла для отправки через Telegram.
     *
     * @param text     текст сообщения
     * @param imageFile файл изображения
     */
    void prepareMessageWithImageFile(String text, File imageFile);

    /**
     * Подготавливает текстовое сообщение для отправки через Telegram.
     *
     * @param text текст сообщения
     */
    void prepareMessageWithText(String text);
}
