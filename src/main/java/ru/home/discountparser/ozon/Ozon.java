package ru.home.discountparser.ozon;

import java.io.File;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Scope("prototype")
public class Ozon {
    private String urlGoods;
    private File screenShot;
    private boolean goodsAvailable = false;
}
