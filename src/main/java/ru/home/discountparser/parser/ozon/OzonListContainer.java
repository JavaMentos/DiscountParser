package ru.home.discountparser.parser.ozon;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.discountparser.parser.ozon.dto.Ozon;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OzonListContainer {
    public static List<Ozon> ozonProducts = new CopyOnWriteArrayList<>();

}
