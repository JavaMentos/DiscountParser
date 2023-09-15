package ru.home.discountparser.ozon;

import ru.home.discountparser.ozon.dto.Ozon;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OzonListContainer {
    public static List<Ozon> ozonProducts = new CopyOnWriteArrayList<>();

}
