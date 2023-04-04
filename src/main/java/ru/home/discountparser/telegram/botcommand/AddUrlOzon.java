package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.TelegramService;
import ru.home.discountparser.telegram.botState.TelegramBotState;

import java.util.function.Consumer;

/**
 * Класс-компонент, обрабатывающий добавление URL товара Ozon.
 * Реализует интерфейс Consumer для обработки сообщений.
 */
@Component
public class AddUrlOzon implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramService telegramService;
    @Autowired
    private TelegramBotState botState;

    /**
     * Обрабатывает сообщение с добавлением ссылки на товар Ozon.
     *
     * @param message объект сообщения, полученного от пользователя.
     */
    @Override
    public void accept(Message message) {
        String response = "Ожидаю ссылку на товар";
        telegramService.sendTextMessage(response);

        botState.changeActivityState(message.getText().replace(telegramService.getBotUsername(), ""));
    }
}