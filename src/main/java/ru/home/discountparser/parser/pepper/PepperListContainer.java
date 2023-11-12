package ru.home.discountparser.parser.pepper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PepperListContainer {
    private static final List<String> alertKeywords = List.of("ноутбук", "электрогитара");
    public static List<String> getAllAlertKeywords() {
        return alertKeywords;
    }

}
