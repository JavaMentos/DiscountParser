package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.pepper.PepperParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class TestCommand implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegram;

    @Override
    public void accept(Message message) {
        String text = String.format("Размер коллекции %n" + PepperParser.currentListPeppers.size());
        telegram.sendMessageText(text);
    }
}