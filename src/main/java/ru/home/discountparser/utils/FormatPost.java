package ru.home.discountparser.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.discountparser.parser.pepper.dto.Pepper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatPost {

    /**
     * Форматирует сообщение с информацией о товаре из объекта Pepper.
     *
     * @param pepper объект Pepper, содержащий информацию о товаре.
     * @return отформатированное сообщение с информацией о товаре.
     */
    public static String formatPepperPostMessage(Pepper pepper) {
        return pepper.getProductDescription() + "\n\n"
                + "Старая цена <s>" + pepper.getOldPrice() + "</s>\n"
                + "Новая цена <b>" + pepper.getNewPrice() + "</b>\n\n"
                + "Описание:\n<i>" + pepper.getDetails() + "</i>\n\n"
                + "<a href=\""  + pepper.getHiddenUrl() + "\">Магазин</a>"
                + " - <i>" + pepper.getDomainShop() + "</i>\n"
                + "<a href=\"" + pepper.getUrl() + "\">ссылка на товар</a>";
    }
}
