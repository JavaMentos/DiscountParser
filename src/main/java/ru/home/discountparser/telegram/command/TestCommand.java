package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.service.MessageSender;

import java.util.function.Consumer;

import static ru.home.discountparser.parser.pepper.PepperListContainer.getPepperPostListSize;

@Component
@AllArgsConstructor
public class TestCommand implements Consumer<Message> {

    private MessageSender telegramMessageSender;

    @Override
    public void accept(Message message) {
        String text = String.format("Размер коллекции %d", getPepperPostListSize());
        telegramMessageSender.prepareMessageWithText(text);
    }
}