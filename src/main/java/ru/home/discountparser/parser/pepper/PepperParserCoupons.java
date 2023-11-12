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

    private static final String PEPPER_URL = "https://www.pepper.ru/coupons";

    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(PEPPER_URL).get();

            Elements postElements = getCouponsElements(document);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private Elements getCouponsElements(Document document) {
        Element contentList = document.selectFirst("div#content-list");
        return contentList.select(".thread.cept-thread-item.thread--type-list.imgFrame-container--scale.thread--voucher.thread--mode-default");
    }

}
