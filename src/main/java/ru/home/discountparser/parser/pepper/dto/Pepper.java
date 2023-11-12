package ru.home.discountparser.parser.pepper.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Класс Pepper представляет собой модель поста.
 * Используется для хранения данных поста в Redis с указанным временем жизни ключа.
 */
@Builder
@Data
@RedisHash(value = "Pepper", timeToLive = 21600L)
public class Pepper {
    @Id
    private String productDescription;
    private String oldPrice;
    private String newPrice;
    private String discountPercentage;
    private String details;
    private String imageUrl;
    private String url;
    private String domainShop;
    private String hiddenUrl;
}
