package ru.home.discountparser.parser.pepper;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class PepperParserCoupons {

    private static final String pepperUrl = "https://www.pepper.ru/coupons";

    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(pepperUrl).get();

            Elements postElements = getCouponsElements(document);

//            for (Element element : postElements) {

//                String productDescription = getProductDescription(element);
//
//                boolean isAlertingProduct = checkIfTitleContainsFavoriteWords(productDescription);
//                boolean isNewPricePercentage = checkIfNewPostExpectedDiscountPrice(element);
//
//                if ((isAlertingProduct || isNewPricePercentage) && isNewPost(productDescription)) {
//                    Pepper newPepperPost = createNewPepperPost(element, true);
//                    addPepperPost(newPepperPost);
//                }
//            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private Elements getCouponsElements(Document document) {
        Element contentList = document.selectFirst("div#content-list");
        return contentList.select(".thread.cept-thread-item.thread--type-list.imgFrame-container--scale.thread--voucher.thread--mode-default");
    }

}
