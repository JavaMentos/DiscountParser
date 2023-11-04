package ru.home.discountparser.parser.pepper.dto;

import java.time.LocalDate;

import lombok.*;
import org.springframework.context.annotation.Scope;

/**
 * Класс Pepper представляет собой модель предложения на сайте Pepper.
 */

@Builder
@Data
public class Pepper {

    private String oldPrice;
    private String newPrice;
    private String discountPercentage;
    private String productDescription;
    private String details;
    private String imageUrl;
    private String url;
    private boolean isNew;
    private LocalDate date;
    private String domainShop;
    private String hiddenUrl;
}
