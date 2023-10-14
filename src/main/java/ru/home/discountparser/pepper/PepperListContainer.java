package ru.home.discountparser.pepper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.discountparser.pepper.dto.Pepper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PepperListContainer {
    public final static List<Pepper> currentPepperPosts = new CopyOnWriteArrayList<>();
    public final static List<String> alertKeywords = List.of("ноутбук");

}
