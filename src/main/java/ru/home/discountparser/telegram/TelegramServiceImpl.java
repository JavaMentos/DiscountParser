package ru.home.discountparser.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.discountparser.telegram.botcommand.*;
import ru.home.discountparser.telegram.message.IncomingMessageProcessing;

import java.io.File;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Класс TelegramServiceImpl предоставляет реализацию интерфейса TelegramService и обрабатывает входящие сообщения от Telegram-бота.
 */
@Component
public class TelegramServiceImpl extends TelegramLongPollingBot implements TelegramService {
    @Value("${bot.parseMode}")
    private String parseMode;
    @Value("${bot.userName}")
    private String userName;
    @Value("${bot.token}")
    private String token;
    @Value("${chat.id}")
    private String chatId;
    @Value("${user.id}")
    private String userId;
    @Autowired
    private IncomingMessageProcessing messageProcessor;

    private HashMap<String, Consumer<Message>> commandMap = new HashMap<>();

    public TelegramServiceImpl(TestCommand testCommand
            , GetChatId getChatId
            , GetCollectionUrlOzon getCollectionUrlOzon
            , AddUrlOzon addUrlOzon
            , ClearCollectionOzon clearCollectionOzon) {
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/getcollectionozon", getCollectionUrlOzon);
        commandMap.put("/addurlozon", addUrlOzon);
        commandMap.put("/clearcollectionozon", clearCollectionOzon);
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

            if (update.hasMessage() && message.hasText()) {
                String currentChatId = message.getChatId().toString();
                String currentUserId = message.getFrom().getId().toString();

                if (currentChatId.equals(chatId)) {
                    String textHasMessage = message.getText().replace(getBotUsername(), "");

                    Consumer<Message> messageConsumer = commandMap.get(textHasMessage);

                    if (messageConsumer != null) {
                        messageConsumer.accept(message);
                        return;
                    }

                    messageProcessor.processMessage(message);
                }
            }
    }

    public void sendTextWithImageUrl(String text, String imageUrl) {
        try {
            execute(SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(imageUrl))
                    .caption(text)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendTextWithImageFile(String text, File imageFile) {
        try {
            execute(SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(imageFile))
                    .caption(text)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public void sendTextMessage(String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode(parseMode)
                    .text(text)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
