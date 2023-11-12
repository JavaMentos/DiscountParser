package ru.home.discountparser.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.discountparser.service.TelegramService;
import ru.home.discountparser.telegram.command.*;
import ru.home.discountparser.telegram.properties.TelegramProperties;

import java.io.File;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Класс TelegramServiceImpl предоставляет реализацию интерфейса TelegramService и обрабатывает
 * входящие сообщения от Telegram-бота.
 */
@Service
public class TelegramServiceImpl extends TelegramLongPollingBot implements TelegramService {

    private final TelegramProperties telegramProperties;
    private final IncomingMessageProcessorImpl messageProcessor;
    private HashMap<String, Consumer<Message>> commandMap = new HashMap<>();

    public TelegramServiceImpl(TestCommand testCommand, GetChatId getChatId,
                               GetCollectionUrlOzon getCollectionUrlOzon,
                               AddUrlOzon addUrlOzon,
                               ClearCollectionOzon clearCollectionOzon,
                               IncomingMessageProcessorImpl messageProcessor,
                               TelegramProperties telegramProperties) {
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/getcollectionozon", getCollectionUrlOzon);
        commandMap.put("/addurlozon", addUrlOzon);
        commandMap.put("/clearcollectionozon", clearCollectionOzon);
        this.messageProcessor = messageProcessor;
        this.telegramProperties = telegramProperties;
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUserName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()) {
            String currentChatId = message.getChatId().toString();

            if (currentChatId.equals(telegramProperties.getChatId())) {
                String textHasMessage = message.getText()
                        .replace(getBotUsername(), "")
                        .replace("@","");

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
            execute(SendPhoto.builder()
                    .chatId(telegramProperties.getChatId())
                    .photo(new InputFile(imageUrl))
                    .caption(text)
                    .parseMode(telegramProperties.getParseMode())
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithImage(String text, File imageFile) {
        try {
            execute(SendPhoto.builder()
                    .chatId(telegramProperties.getChatId())
                    .photo(new InputFile(imageFile))
                    .caption(text)
                    .parseMode(telegramProperties.getParseMode())
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public void sendTextMessage(String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(telegramProperties.getChatId())
                    .parseMode(telegramProperties.getParseMode())
                    .text(text)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
