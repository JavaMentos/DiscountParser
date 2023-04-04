package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramService;

import java.util.function.Consumer;

/**
 * Класс-компонент, обрабатывающий очистку коллекции товаров Ozon.
 * Реализует интерфейс Consumer для обработки сообщений.
 */
@Component
public class ClearCollectionOzon implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramService telegramService;

    /**
     * Обрабатывает сообщение с запросом на очистку коллекции товаров Ozon.
     *
     * @param message объект сообщения, полученного от пользователя.
     */
    @Override
    public void accept(Message message) {

        OzonParser.ozonProducts.clear();
        telegramService.sendTextMessage("Коллекция очищена");
    }
}