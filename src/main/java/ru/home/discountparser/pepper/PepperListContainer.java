package ru.home.discountparser.pepper;

import ru.home.discountparser.pepper.dto.Pepper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PepperListContainer {
    public final static List<Pepper> currentPepperPosts = new CopyOnWriteArrayList<>();
    public final static List<String> alertKeywords = List.of("ноутбук");

}
