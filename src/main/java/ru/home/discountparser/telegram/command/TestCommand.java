package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.message.TelegramMessageSender;

import java.util.function.Consumer;

import static ru.home.discountparser.pepper.PepperListContainer.currentPepperPosts;

@Component
@AllArgsConstructor
public class TestCommand implements Consumer<Message> {

    private TelegramMessageSender telegramMessageSender;

    @Override
    public void accept(Message message) {
        String text = String.format("Размер коллекции %d", currentPepperPosts.size());
        telegramMessageSender.sendTextMessage(text);
    }
}