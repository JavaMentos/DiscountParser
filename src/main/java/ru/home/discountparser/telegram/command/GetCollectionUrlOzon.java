package ru.home.discountparser.telegram.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.telegram.service.MessageSender;

import java.util.function.Consumer;

import static ru.home.discountparser.ozon.OzonListContainer.ozonProducts;

/**
 * Класс GetCollectionUrlOzon обрабатывает полученное сообщение и отправляет коллекцию ссылок на продукты с Ozon.
 * Реализует интерфейс Consumer<Message>.
 */
@Component
@AllArgsConstructor
public class GetCollectionUrlOzon implements Consumer<Message> {

    private MessageSender telegramMessageSender;

    /**
     * Обрабатывает полученное сообщение и отправляет коллекцию ссылок на продукты с Ozon.
     *
     * @param message принимает сообщение от пользователя
     */
    @Override
    public void accept(Message message) {
        telegramMessageSender.prepareMessageWithText(formatOzonUrlList());
    }

    /**
     * Создает отформатированный список ссылок на продукты с Ozon.
     *
     * @return отформатированную строку со списком ссылок на продукты
     */
    private String formatOzonUrlList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Всего ссылок: ")
                .append(ozonProducts.size())
                .append(" - https://www.ozon.ru")
                .append("\n\n");

        ozonProducts.forEach(ozon ->
                stringBuilder.append(ozon.getProductUrl())
                        .append("\n\n")
        );
        return stringBuilder.toString();
    }
}