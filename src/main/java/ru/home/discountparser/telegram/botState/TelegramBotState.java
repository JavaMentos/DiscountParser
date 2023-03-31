package ru.home.discountparser.telegram.botState;

import org.springframework.stereotype.Component;

@Component
public class TelegramBotState {
    private BotState state;
    private long userIdForState;

    public TelegramBotState() {
        setActivityBot(BotState.FREE_STATE);
    }

    public void setActivityBot(BotState state) {
        this.state = state;
    }

    public void changeActivityState(String s) {
        if (s.equalsIgnoreCase("/addurlozon")) {
            setActivityBot(BotState.WAITING_URL_FOR_OZON);
        }
    }

    public void stateFree() {
        setActivityBot(BotState.FREE_STATE);
    }

    public BotState getState() {
        return state;
    }

    public long getUserIdForState() {
        return userIdForState;
    }

    public void setUserIdForState(long userIdForState) {
        this.userIdForState = userIdForState;
    }

    public enum BotState {
        FREE_STATE,
        WAITING_URL_FOR_OZON
    }
}