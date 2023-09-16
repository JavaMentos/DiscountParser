package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.message.MessageSender;

import java.util.function.Consumer;

/**
 * Класс GetChatId обрабатывает полученное сообщение и отправляет идентификатор чата и пользователя.
 * Реализует интерфейс Consumer<Message>.
 */
@Component
@AllArgsConstructor
public class GetChatId implements Consumer<Message> {

    private MessageSender telegramMessageSender;

    /**
     * Обрабатывает полученное сообщение и отправляет идентификатор чата и пользователя.
     *
     * @param message принимает сообщение от пользователя
     */
    @Override
    public void accept(Message message) {
        String format = String.format("ID chat: %s %nUser ID: %s", message.getChatId(), message.getFrom().getId());
        telegramMessageSender.prepareMessageWithText(format);
    }
}