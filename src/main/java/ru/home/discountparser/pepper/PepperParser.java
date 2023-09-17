package ru.home.discountparser.pepper;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.home.discountparser.pepper.dto.Pepper;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.util.List;

import static ru.home.discountparser.pepper.PepperListContainer.alertKeywords;
import static ru.home.discountparser.pepper.PepperListContainer.currentPepperPosts;

/**
 * Класс PepperParser отслеживает новые скидки на товары на сайте pepper.ru.
 * Он оповещает об определенных скидках и товарах на основе заданных параметров.
 */
@Component
@Log4j2
public class PepperParser {
    private final String pepperUrl = "https://www.pepper.ru/new";
    private final double alertingPricePercentage = 30;

    /**
     * Метод проверяет новые публикации на сайте и добавляет их в список новых публикаций
     * если они соответствуют заданным условиям.
     */
    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(pepperUrl).get();

            Elements postElements = getPostElements(document);

            for (Element element : postElements) {

                String productDescription = getProductDescription(element);

                boolean isAlertingProduct = checkIfTitleContainsFavoriteWords(productDescription);
                boolean isNewPricePercentage = checkIfNewPostExpectedDiscountPrice(element);

                if ((isAlertingProduct || isNewPricePercentage) && isNewPost(productDescription)) {
                        Pepper newPepperPost = createNewPepperPost(element, true);
                        currentPepperPosts.add(newPepperPost);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * Создает новый объект Pepper с указанными параметрами.
     *
     * @param element           - объект из которого берутся данные по посту
     * @param isAlertingProduct параметр чтобы добавить в оповвещение
     * @return новый объект Pepper.
     */
    private Pepper createNewPepperPost(Element element, boolean isAlertingProduct) {
        String oldPriceString = element.select("span[class=mute--text text--lineThrough size--all-l size--fromW3-xl]").text();
        String newPriceString = element.select("span[class=thread-price text--b cept-tp size--all-l size--fromW3-xl]").text();
        String discountPercentage = element.select("span[class=space--ml-1 size--all-l size--fromW3-xl]").text();
        String productDescription = getProductDescription(element);
        String details = element.select("div.overflow--wrap-break.width--all-12.size--all-s[data-handler='lightbox-xhr emoticon-preview']").text();
        String imageUrl = element.select("img").attr("src");
        String url = getUrl(element);

        return Pepper.builder()
                .oldPrice(oldPriceString)
                .newPrice(newPriceString)
                .discountPercentage(discountPercentage)
                .productDescription(productDescription)
                .details(details)
                .imageUrl(imageUrl)
                .url(url)
                .isNew(isAlertingProduct)
                .date(LocalDate.now())
                .build();
    }

    private Elements getPostElements(Document document) {
        Element contentList = document.selectFirst("div[id=content-list]");
        return contentList.select("div[class=threadGrid thread-clickRoot]");
    }

    private String getProductDescription(Element element) {
        return element.select("a").attr("title");
    }

    private String getUrl(Element element) {
        String postNumber = element.parentNode().attributes().get("id").replace("thread_", "");
        String intermediateUrl = "https://www.pepper.ru/visit/homenew/" + postNumber;

        try {
            Document doc = Jsoup.connect(intermediateUrl).followRedirects(true).get();
            return doc.location();
        } catch (HttpStatusException e) {
            return e.getUrl();
        } catch (ConnectException e) {
            log.error(e.getMessage(), e);
            return intermediateUrl;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private boolean checkIfNewPostExpectedDiscountPrice(Element element) {
        String discountPercentage = element.select("span[class=space--ml-1 size--all-l size--fromW3-xl]").text();
        if (Strings.isNullOrEmpty(discountPercentage)) {
            return false;
        }
        Double percentage = parseStringPercentage(discountPercentage);
        return percentage >= alertingPricePercentage;
    }

    private boolean checkIfTitleContainsFavoriteWords(String productDescription) {
        for (String keyword : alertKeywords) {
            if (productDescription.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
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
     * @param productDescription строка для проверки, был такой пост или нет.
     * @return true, если объект Pepper является новой публикацией, иначе false.
     */
    private boolean isNewPost(String productDescription) {
        return currentPepperPosts.stream()
                .noneMatch(d -> d.getProductDescription().equals(productDescription));
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
                + "<a href=\"" + pepper.getUrl() + "\">ссылка на товар</a>";
    }
}
