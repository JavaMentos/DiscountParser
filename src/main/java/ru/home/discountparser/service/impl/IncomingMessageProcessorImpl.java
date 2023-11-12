package ru.home.discountparser.service.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.parser.ozon.dto.Ozon;
import ru.home.discountparser.service.MessageSender;
import ru.home.discountparser.telegram.state.TelegramBotState;

import static ru.home.discountparser.parser.ozon.OzonListContainer.OZON_PRODUCTS;

/**
 * Класс IncomingMessageProcessing обрабатывает входящие сообщения от пользователей.
 */
@Service
public class IncomingMessageProcessorImpl {

    @Autowired
    private TelegramBotState botState;

    @Autowired
    @Lazy
    private MessageSender messageSender;

    public void processMessage(Message message) {

        String messageText = message.getText();

        switch (botState.getState()) {
            case WAITING_URL_FOR_OZON:
                if (!isValidUrl(messageText)) {
                    messageSender.prepareMessageWithText("Неверный формат ссылки");
                    botState.stateFree();
                    break;
                }

                addOzonUrl(messageText);
                messageSender.prepareMessageWithText("Ссылка добавлена");

                botState.stateFree();
                break;

            default:
                botState.stateFree();
                break;
        }
    }

    private boolean isValidUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    private void addOzonUrl(String url) {
        Ozon ozon = new Ozon();
        ozon.setProductUrl(url);
        OZON_PRODUCTS.add(ozon);
    }
}
