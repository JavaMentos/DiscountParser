package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.repository.PepperRepository;
import ru.home.discountparser.service.MessageSender;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class TestCommand implements Consumer<Message> {

    private MessageSender telegramMessageSender;
    private final PepperRepository pepperRepository;

    @Override
    public void accept(Message message) {
        String text = String.format("Размер коллекции %d", pepperRepository.count());
        telegramMessageSender.prepareMessageWithText(text);
    }
}