package ru.home.discountparser.telegram.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.dto.Ozon;
import ru.home.discountparser.telegram.message.MessageSender;
import ru.home.discountparser.telegram.state.TelegramBotState;

import static ru.home.discountparser.ozon.OzonListContainer.ozonProducts;

/**
 * Класс IncomingMessageProcessing обрабатывает входящие сообщения от пользователей.
 */
@Component
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
        String regex = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
        return url.matches(regex);
    }

    private void addOzonUrl(String url) {
        Ozon ozon = new Ozon();
        ozon.setProductUrl(url);
        ozonProducts.add(ozon);
    }
}
