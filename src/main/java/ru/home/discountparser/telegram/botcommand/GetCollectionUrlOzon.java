package ru.home.discountparser.telegram.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramService;

import java.util.function.Consumer;

/**
 * Класс GetCollectionUrlOzon обрабатывает полученное сообщение и отправляет коллекцию ссылок на продукты с Ozon.
 * Реализует интерфейс Consumer<Message>.
 */
@Component
public class GetCollectionUrlOzon implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramService telegramService;

    /**
     * Обрабатывает полученное сообщение и отправляет коллекцию ссылок на продукты с Ozon.
     *
     * @param message принимает сообщение от пользователя
     */
    @Override
    public void accept(Message message) {
        telegramService.sendTextMessage(formatOzonUrlList());
    }

    /**
     * Создает отформатированный список ссылок на продукты с Ozon.
     *
     * @return отформатированную строку со списком ссылок на продукты
     */
    private String formatOzonUrlList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Всего ссылок: ")
                .append(OzonParser.ozonProducts.size())
                .append(" - https://www.ozon.ru")
                .append("\n\n");

        OzonParser.ozonProducts.forEach(ozon ->
            stringBuilder.append(ozon.getProductUrl())
                    .append("\n\n")
        );

        return stringBuilder.toString();
    }
}