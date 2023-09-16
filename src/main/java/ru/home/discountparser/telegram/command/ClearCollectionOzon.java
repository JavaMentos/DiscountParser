package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.message.MessageSender;

import java.util.function.Consumer;

import static ru.home.discountparser.ozon.OzonListContainer.ozonProducts;

/**
 * Класс-компонент, обрабатывающий очистку коллекции товаров Ozon.
 * Реализует интерфейс Consumer для обработки сообщений.
 */
@Component
@AllArgsConstructor
public class ClearCollectionOzon implements Consumer<Message> {
    private MessageSender telegramMessageSender;

    /**
     * Обрабатывает сообщение с запросом на очистку коллекции товаров Ozon.
     *
     * @param message объект сообщения, полученного от пользователя.
     */
    @Override
    public void accept(Message message) {

        ozonProducts.clear();
        telegramMessageSender.prepareMessageWithText("Коллекция очищена");
    }
}