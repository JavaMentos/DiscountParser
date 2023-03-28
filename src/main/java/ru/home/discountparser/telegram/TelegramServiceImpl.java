package ru.home.discountparser.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.discountparser.scheduler.SchedulerPepper;
import ru.home.discountparser.selenium.Selenium;

import java.io.File;

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

    @Autowired
    SchedulerPepper schedulerPepper;

    @Autowired
    private Selenium selenium;

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


    }

    public void sendMessageTextAndPhoto(String text, String imageUrl) {
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

    public void sendMessageTextAndPhoto(String text, File file) {
        try {
            execute(SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(file))
                    .caption(text)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }
}
