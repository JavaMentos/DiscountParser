package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.TelegramServiceImpl;
import ru.home.discountparser.telegram.botState.TelegramBotState;

import java.util.function.Consumer;

@Component
public class AddUrlOzon implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegram;
    @Autowired
    private TelegramBotState botState;

    @Override
    public void accept(Message message) {
        String answer = "Ожидаю ссылку на товар";
        telegram.sendMessageText(answer);

        botState.changeActivityState(message.getText().replace(telegram.getBotUsername(), ""));
    }
}