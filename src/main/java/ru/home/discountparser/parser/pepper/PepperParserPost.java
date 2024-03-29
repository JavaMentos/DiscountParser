package ru.home.discountparser.parser.pepper;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.home.discountparser.parser.pepper.dto.Pepper;
import ru.home.discountparser.service.PepperService;

import java.io.IOException;
import java.net.ConnectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.home.discountparser.parser.pepper.PepperListContainer.getAllAlertKeywords;

/**
 * Класс PepperParser отслеживает новые скидки на товары на сайте pepper.ru.
 * Он оповещает об определенных скидках и товарах на основе заданных параметров.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class PepperParserPost {
    private static final String PEPPER_URL = "https://www.pepper.ru/new";
    private static final double ALERTING_PRICE_PERCENTAGE = 30;
    private final PepperService pepperService;


    /**
     * Метод проверяет новые публикации на сайте и добавляет их в список новых публикаций
     * если они соответствуют заданным условиям.
     */
    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(PEPPER_URL).get();

            Elements postElements = getPostElements(document);

            for (Element element : postElements) {

                String productDescription = getProductDescription(element);

                boolean isAlertingProduct = checkIfTitleContainsFavoriteWords(productDescription);
                boolean isNewPricePercentage = checkIfNewPostExpectedDiscountPrice(element);

                if ((isAlertingProduct || isNewPricePercentage) && !pepperService.existsById(productDescription)) {
                    Pepper newPepperPost = createNewPepperPost(element);
                    pepperService.addPost(newPepperPost);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * Создает новый объект Pepper с указанными параметрами.
     *
     * @param element - объект из которого берутся данные по посту
     * @return новый объект Pepper.
     */
    private Pepper createNewPepperPost(Element element) {
        String oldPriceString = element.select("span[class=mute--text text--lineThrough size--all-l size--fromW3-xl]").text();
        String newPriceString = element.select("span[class=thread-price text--b cept-tp size--all-l size--fromW3-xl]").text();
        String discountPercentage = element.select("span[class=space--ml-1 size--all-l size--fromW3-xl]").text();
        String productDescription = getProductDescription(element);
        String details = element.select("div.overflow--wrap-break.width--all-12.size--all-s[data-handler='lightbox-xhr emoticon-preview']").text();
        String imageUrl = element.select("img").attr("src");
        String url = getUrl(element);
        String domainShop = extractDomain(url);
        String hiddenUrl = element.select("a").attr("href");

        return Pepper.builder()
                .oldPrice(oldPriceString)
                .newPrice(newPriceString)
                .discountPercentage(discountPercentage)
                .productDescription(productDescription)
                .details(details)
                .imageUrl(imageUrl)
                .url(url)
                .domainShop(domainShop)
                .hiddenUrl(hiddenUrl)
                .build();
    }

    public String extractDomain(String url) {
        String domain = "";
        Pattern pattern = Pattern.compile("^(?:https?://)?(?:www\\.)?([^/]+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            domain = matcher.group(1);
        }

        return domain;
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
        return percentage >= ALERTING_PRICE_PERCENTAGE;
    }

    private boolean checkIfTitleContainsFavoriteWords(String productDescription) {
        for (String keyword : getAllAlertKeywords()) {
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
}
