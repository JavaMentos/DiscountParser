package ru.home.discountparser.parser.pepper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.discountparser.parser.pepper.dto.Pepper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PepperListContainer {
    private static final List<Pepper> currentPepperPosts = new CopyOnWriteArrayList<>();
    private static final List<Pepper> currentCouponPosts = new CopyOnWriteArrayList<>();
    private static final List<String> alertKeywords = List.of("ноутбук");

    public static List<String> getAllAlertKeywords() {
        return alertKeywords;
    }

    public static void addPepperPost(Pepper pepper) {
        currentPepperPosts.add(pepper);
    }

    public static List<Pepper> getAllPepperPosts() {
        return currentPepperPosts;
    }

    public static boolean isPepperPostListEmpty() {
        return currentPepperPosts.isEmpty();
    }

    public static int getPepperPostListSize() {
        return currentPepperPosts.size();
    }

}
