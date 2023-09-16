package ru.home.discountparser.pepper.dto;

import java.time.LocalDate;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Класс Pepper представляет собой модель предложения на сайте Pepper.
 */
@Component
@Builder
@Data
@Scope("prototype")
public class Pepper {

    private String oldPrice;
    private String newPrice;
    private String discountPercentage;
    private String productDescription;
    private String details;
    private String imageUrl;
    private String url;
    private boolean isNew = true;
    private LocalDate date = LocalDate.now();
}
