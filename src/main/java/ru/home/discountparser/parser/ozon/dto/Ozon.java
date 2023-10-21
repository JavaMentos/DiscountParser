package ru.home.discountparser.parser.ozon.dto;

import java.io.File;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Класс Ozon представляет товар на сайте Ozon с информацией о наличии,
 * URL товара и скриншотом.
 */
@Data
@Component
@NoArgsConstructor
@Scope("prototype")
public class Ozon {
    private String productUrl;
    private File screenShot;
    private boolean isAvailable = false;
}
