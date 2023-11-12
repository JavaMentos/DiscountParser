package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.service.MessageSender;
import ru.home.discountparser.telegram.state.TelegramBotState;

import java.util.function.Consumer;

/**
 * Класс-компонент, обрабатывающий добавление URL товара Ozon.
 * Реализует интерфейс Consumer для обработки сообщений.
 */
@Component
@AllArgsConstructor
public class AddUrlOzon implements Consumer<Message> {

    private MessageSender telegramMessageSender;
    private TelegramBotState botState;

    /**
     * Обрабатывает сообщение с добавлением ссылки на товар Ozon.
     *
     * @param message объект сообщения, полученного от пользователя.
     */
    @Override
    public void accept(Message message) {
        String response = "Ожидаю ссылку на товар";
        telegramMessageSender.prepareMessageWithText(response);

        botState.changeActivityState(message.getText());
    }
}