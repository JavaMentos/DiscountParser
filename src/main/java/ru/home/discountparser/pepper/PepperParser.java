package ru.home.discountparser.pepper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

/**
 * Класс PepperParser отслеживает новые скидки на товары на сайте pepper.ru.
 * Он оповещает об определенных скидках и товарах на основе заданных параметров.
 */
@Component
public class PepperParser {

    public static CopyOnWriteArrayList<Pepper> newPepperPosts = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Pepper> currentPepperPosts = new CopyOnWriteArrayList<>();
    private final String pepperUrl = "https://www.pepper.ru/new";
    private final double alertingPricePercentage = 30;
    private final String alertKeyword = "ноутбук";

    /**
     * Метод проверяет новые публикации на сайте и добавляет их в список новых публикаций
     * если они соответствуют заданным условиям.
     */
    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(pepperUrl).get();

            Element contentList = document.select("div[id=content-list]").first();
            Elements elementPost = contentList.select("div[class=threadGrid thread-clickRoot]");

            for (Element element : elementPost) {

                String oldPriceString
                        = element.select("span[class=mute--text text--lineThrough size--all-l size--fromW3-xl]").text();
                String newPriceString
                        = element.select("span[class=thread-price text--b cept-tp size--all-l size--fromW3-xl]").text();
                String discountPercentage
                        = element.select("span[class=space--ml-1 size--all-l size--fromW3-xl]").text();
                String productDescription
                        = element.select("a").attr("title");
                String details
                        = element.select("div[class=overflow--wrap-break width--all-12  size--all-s size--fromW3-m]").text();
                String imageUrl
                        = element.select("img").attr("src");
                String url
                        = element.select("a").attr("href");

                if (productDescription.equalsIgnoreCase(alertKeyword)) {
                    Pepper newPepperPost = createNewPepperPost(oldPriceString,
                            newPriceString,
                            discountPercentage,
                            productDescription,
                            details,
                            imageUrl,
                            url,
                            true);

                    if (!isNewPost(newPepperPost)) newPepperPosts.add(newPepperPost);
                    return;
                }

                if (Strings.isNullOrEmpty(discountPercentage)) {
                    return;
                }

                Double percentage = parseStringPercentage(discountPercentage);
                if (percentage >= alertingPricePercentage) {
                    Pepper newPepperPost = createNewPepperPost(oldPriceString
                            , newPriceString
                            , discountPercentage
                            , productDescription
                            , details
                            , imageUrl
                            , url
                            , true);
                    if (!isNewPost(newPepperPost)) newPepperPosts.add(newPepperPost);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println(LocalDateTime.now() + " PepperParser");
        }

    }

    /**
     * Преобразует строку с процентами скидки в число типа Double.
     *
     * @param discountPercentage строка с процентами скидки.
     * @return процент скидки в виде числа типа Double.
     */
    private Double parseStringPercentage(String discountPercentage) {
        String replace = discountPercentage
                .replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace("%", "");
        return Double.parseDouble(replace);
    }

    /**
     * Проверяет, является ли указанный объект Pepper новой публикацией.
     *
     * @param pepper объект Pepper для проверки.
     * @return true, если объект Pepper является новой публикацией, иначе false.
     */
    private boolean isNewPost(Pepper pepper) {
        return currentPepperPosts.stream()
                .anyMatch(d -> d.getProductDescription().equals(pepper.getProductDescription()));
    }

    /**
     * Создает новый объект Pepper с указанными параметрами.
     *
     * @param oldPrice старая цена товара.
     * @param newPrice новая цена товара.
     * @param discountPercentage процент скидки.
     * @param productDescription описание товара.
     * @param details дополнительная информация о товаре.
     * @param imageUrl URL изображения товара.
     * @param url URL страницы товара.
     * @param isNew флаг, указывающий, является ли публикация новой.
     * @return новый объект Pepper.
     */
    private Pepper createNewPepperPost(String oldPrice, String newPrice, String discountPercentage,
                                       String productDescription, String details, String imageUrl,
                                       String url, boolean isNew) {
        return Pepper.builder()
                .oldPrice(oldPrice)
                .newPrice(newPrice)
                .discountPercentage(discountPercentage)
                .productDescription(productDescription)
                .details(details)
                .imageUrl(imageUrl)
                .url(url)
                .isNew(isNew)
                .build();
    }

    /**
     * Форматирует сообщение с информацией о товаре из объекта Pepper.
     *
     * @param pepper объект Pepper, содержащий информацию о товаре.
     * @return отформатированное сообщение с информацией о товаре.
     */
    public String formatPepperPostMessage(Pepper pepper) {
        return pepper.getProductDescription() + "\n\n"
                + "Старая цена <s>" + pepper.getOldPrice() + "</s>\n"
                + "Новая цена <b>" + pepper.getNewPrice() + "</b>\n\n"
                + "Описание:\n<i>" + pepper.getDetails() + "</i>\n\n"
                + "Ссылка на товар\n" + pepper.getUrl();
    }
}
