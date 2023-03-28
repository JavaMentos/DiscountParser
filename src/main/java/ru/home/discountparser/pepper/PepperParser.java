package ru.home.discountparser.pepper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class PepperParser {

    public static CopyOnWriteArrayList<Pepper> newListPeppers = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Pepper> currentListPeppers = new CopyOnWriteArrayList<>();
    private final String urlPepper = "https://www.pepper.ru/new";
    private final double alertingPricePercent = 30;
    private final String textForAlert = "ноутбук";

    public void checkNewPosts() {

        try {
            Document document = Jsoup.connect(urlPepper).get();

            Element contentList = document.select("div[id=content-list]").first();
            Elements elementPost = contentList.select("div[class=threadGrid thread-clickRoot]");

            for (Element element : elementPost) {

                String priceOldString
                        = element.select("span[class=mute--text text--lineThrough size--all-l size--fromW3-xl]").text();
                String priceNewString
                        = element.select("span[class=thread-price text--b cept-tp size--all-l size--fromW3-xl]").text();
                String percentDiscount
                        = element.select("span[class=space--ml-1 size--all-l size--fromW3-xl]").text();
                String productDescription
                        = element.select("a").attr("title");
                String description
                        = element.select("div[class=overflow--wrap-break width--all-12  size--all-s size--fromW3-m]").text();
                String imageUrl
                        = element.select("img").attr("src");
                String url
                        = element.select("a").attr("href");

                if (productDescription.equalsIgnoreCase(textForAlert)) {
                    Pepper newPepperPost = createNewPepperPost(priceOldString,
                            priceNewString,
                            percentDiscount,
                            productDescription,
                            description,
                            imageUrl, url, true);

                    if (!checkIsNewPost(newPepperPost)) newListPeppers.add(newPepperPost);
                    return;
                }

                if (Strings.isNullOrEmpty(percentDiscount)) {
                    return;
                }

                Double percent = parseStringPercent(percentDiscount);
                if (percent >= alertingPricePercent) {
                    Pepper newPepperPost = createNewPepperPost(priceOldString, priceNewString
                            , percentDiscount, productDescription
                            , description, imageUrl, url, true);
                    if (!checkIsNewPost(newPepperPost)) newListPeppers.add(newPepperPost);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println(LocalDateTime.now() + " PepperParser");
        }

    }

    private Double parseStringPercent(String percentDiscount) {
        String replace = percentDiscount
                .replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace("%", "");
        return Double.parseDouble(replace);
    }

    private boolean checkIsNewPost(Pepper pepper) {
        return currentListPeppers.stream().anyMatch(d -> d.getProductDescription().equals(pepper.getProductDescription()));
    }

    private Pepper createNewPepperPost(String priceOld, String priceNew, String percentDiscount, String productDescription,
                                       String description, String imageUrl, String Url, boolean newPost) {
        return new Pepper.Builder()
                .setPriceOld(priceOld)
                .setPriceNew(priceNew)
                .setPercentDiscount(percentDiscount)
                .setProductDescription(productDescription)
                .setDescription(description)
                .setImageUrl(imageUrl)
                .setUrl(Url)
                .setNewPost(newPost)
                .build();
    }

    public String prettyStringPepperPosts(Pepper pr) {
        return pr.getProductDescription() + "\n\n"
                + "Старая цена <s>" + pr.getPriceOld() + "</s>\n"
                + "Новая цена <b>" + pr.getPriceNew() + "</b>\n\n"
                + "Описание:\n<i>" + pr.getDescription() + "</i>\n\n"
                + "Ссылка на товар\n" + pr.getUrl();
    }
}
