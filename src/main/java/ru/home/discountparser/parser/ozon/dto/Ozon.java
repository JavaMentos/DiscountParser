package ru.home.discountparser.parser.ozon.dto;

import java.io.File;

import lombok.*;

/**
 * Класс Ozon представляет товар на сайте Ozon с информацией о наличии,
 * URL товара и скриншотом.
 */
@Data
@NoArgsConstructor
public class Ozon {
    private String productUrl;
    private File screenShot;
    private boolean isAvailable = false;
}
