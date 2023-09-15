package ru.home.discountparser.telegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {
    private String parseMode;
    private String userName;
    private String token;
    private String chatId;
    private String userId;
}
