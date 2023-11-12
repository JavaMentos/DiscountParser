package ru.home.discountparser.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.home.discountparser.service.impl.TelegramServiceImpl;

/**
 * Класс BotInitializer инициализирует и регистрирует телеграм-бота при запуске приложения.
 */
@Configuration
@Log4j2
public class BotInitializer {
    private TelegramServiceImpl telegramBot;

    /**
     * Устанавливает экземпляр TelegramServiceImpl в BotInitializer.
     *
     * @param telegramBot экземпляр TelegramServiceImpl для установки
     */
    @Autowired
    @Lazy
    public void setTelegramBot(TelegramServiceImpl telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод init инициализирует и регистрирует телеграм-бота при запуске приложения.
     *
     * @throws TelegramApiException если возникает ошибка при работе с Telegram API
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiRequestException e) {
            log.error(e.getMessage(), e);
        }
    }
}