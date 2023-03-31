package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class GetCollectionUrlOzon implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegram;

    @Override
    public void accept(Message message) {

        telegram.sendMessageText(prettyListUrlOzon());
    }

    private String prettyListUrlOzon() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Всего ссылок: ")
                .append(OzonParser.listOzons.size())
                .append(" - https://www.ozon.ru")
                .append("\n\n");

        OzonParser.listOzons.forEach(ozon ->
            stringBuilder.append(ozon.getUrlGoods())
                    .append("\n\n")
        );

        return stringBuilder.toString();
    }
}