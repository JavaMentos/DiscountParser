package ru.home.discountparser.telegram.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramService;
import ru.home.discountparser.telegram.botState.TelegramBotState;

/**
 * Класс IncomingMessageProcessing обрабатывает входящие сообщения от пользователей.
 */
@Component
public class IncomingMessageProcessing {

    @Autowired
    private TelegramBotState botState;
    @Autowired
    @Lazy
    private TelegramService telegramService;

    /**
     * Обрабатывает входящие сообщения в соответствии с текущим состоянием бота.
     *
     * @param message входящее сообщение от пользователя
     */
    public void processMessage(Message message) {

        String messageText = message.getText().replace(telegramService.getBotUsername(), "");

        switch (botState.getState()) {
            case WAITING_URL_FOR_OZON:
                if (!isValidUrl(messageText)) {
                    telegramService.sendTextMessage("Неверный формат ссылки");
                    botState.stateFree();
                    break;
                }

                addOzonUrl(messageText);
                telegramService.sendTextMessage("Ссылка добавлена");

                botState.stateFree();
                break;

            default:
                botState.stateFree();
                break;
        }
    }

    /**
     * Проверяет, является ли переданный текст допустимым URL.
     *
     * @param url текст для проверки
     * @return true, если текст является допустимым URL, иначе false
     */
    private boolean isValidUrl(String url) {
        String regex = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
        return url.matches(regex);
    }

    /**
     * Добавляет URL Ozon-товара в список.
     *
     * @param url URL товара Ozon
     */
    private void addOzonUrl(String url) {
        Ozon ozon = new Ozon();
        ozon.setProductUrl(url);
        OzonParser.ozonProducts.add(ozon);
    }
}
