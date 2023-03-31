package ru.home.discountparser.telegram.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.discountparser.ozon.Ozon;
import ru.home.discountparser.ozon.OzonParser;
import ru.home.discountparser.telegram.TelegramServiceImpl;
import ru.home.discountparser.telegram.botState.TelegramBotState;

@Component
public class IncomingMessageProcessing {

    @Autowired
    private TelegramBotState botState;
    @Autowired
    @Lazy
    private TelegramServiceImpl telegram;

    public void messageProcessing(Message message) {

        String textHasMessage = message.getText().replace(telegram.getBotUsername(), "");

        switch (botState.getState()) {

            case WAITING_URL_FOR_OZON:
                if (!checkValidUrl(textHasMessage)) {
                    telegram.sendMessageText("Неверный формат ссылки");
                    botState.stateFree();
                    break;
                }

                addUrlOzon(textHasMessage);
                telegram.sendMessageText("Ссылка добавлена");

                botState.stateFree();
                break;

            default:
                botState.stateFree();
                break;
        }
    }

    private boolean checkValidUrl(String url) {
        String regex = "(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
        return url.matches(regex);
    }

    private void addUrlOzon(String url) {
        Ozon ozon = new Ozon();
        ozon.setUrlGoods(url);
        OzonParser.listOzons.add(ozon);
    }
}
