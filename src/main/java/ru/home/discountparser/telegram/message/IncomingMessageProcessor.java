package ru.home.discountparser.telegram.message;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface IncomingMessageProcessor {


    /**
     * Обрабатывает входящие сообщения в соответствии с текущим состоянием бота.
     *
     * @param message входящее сообщение от пользователя
     */
    void processMessage(Message message);

    /**
     * Проверяет, является ли переданный текст допустимым URL.
     *
     * @param url текст для проверки
     * @return true, если текст является допустимым URL, иначе false
     */
    boolean isValidUrl(String url);

    /**
     * Добавляет URL Ozon-товара в список.
     *
     * @param url URL товара Ozon
     */
    void addOzonUrl(String url);
}
