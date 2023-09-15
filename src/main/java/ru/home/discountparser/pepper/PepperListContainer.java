package ru.home.discountparser.pepper;

import ru.home.discountparser.pepper.dto.Pepper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PepperListContainer {
    public static List<Pepper> newPepperPosts = new CopyOnWriteArrayList<>();
    public static List<Pepper> currentPepperPosts = new CopyOnWriteArrayList<>();
}
