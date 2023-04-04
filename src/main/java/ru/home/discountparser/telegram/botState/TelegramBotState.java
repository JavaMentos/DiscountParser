package ru.home.discountparser.telegram.botState;

import org.springframework.stereotype.Component;

/**
 * Класс TelegramBotState управляет состоянием бота в зависимости от действий пользователя.
 */
@Component
public class TelegramBotState {
    private BotState state;
    private long userIdForState;

    /**
     * Конструктор по умолчанию устанавливает состояние бота на свободное состояние (FREE_STATE).
     */
    public TelegramBotState() {
        setActivityBot(BotState.FREE_STATE);
    }

    /**
     * Устанавливает текущее состояние бота.
     *
     * @param state состояние бота
     */
    public void setActivityBot(BotState state) {
        this.state = state;
    }

    /**
     * Изменяет состояние бота на основе полученной команды.
     *
     * @param command команда от пользователя
     */
    public void changeActivityState(String command) {
        if (command.equalsIgnoreCase("/addurlozon")) {
            setActivityBot(BotState.WAITING_URL_FOR_OZON);
        }
    }

    /**
     * Устанавливает состояние бота на свободное состояние (FREE_STATE).
     */
    public void stateFree() {
        setActivityBot(BotState.FREE_STATE);
    }

    /**
     * Возвращает текущее состояние бота.
     *
     * @return состояние бота
     */
    public BotState getState() {
        return state;
    }

    /**
     * Возвращает ID пользователя, связанного с текущим состоянием бота.
     *
     * @return ID пользователя
     */
    public long getUserIdForState() {
        return userIdForState;
    }

    /**
     * Устанавливает ID пользователя, связанного с текущим состоянием бота.
     *
     * @param userIdForState ID пользователя
     */
    public void setUserIdForState(long userIdForState) {
        this.userIdForState = userIdForState;
    }

    /**
     * Перечисление возможных состояний бота.
     */
    public enum BotState {
        FREE_STATE,
        WAITING_URL_FOR_OZON
    }
}