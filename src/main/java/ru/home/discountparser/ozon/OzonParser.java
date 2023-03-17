//package ru.home.discountparser.ozon;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Component;
//
//import ru.home.discountparser.pepper.Pepper;
//
//@Component
//public class OzonParser {
//    public static List<Ozon> newListPeppers = new ArrayList<>();
//    private final String urlOzon = "https://www.ozon.ru/product/trenazher-dlya-doma-900-corength-decathlon-639989756/?oos_search=false&sh=4zzF_RL_dQ";
////    private final double alertingPricePercent = 30;
////    private final String textForAlert = "ноутбук";
//
//    public void checkNewPosts() {
//        try {
//            Document document = Jsoup.connect(urlOzon).get();
//            System.out.println(document.toString());
////div[class=threadGrid thread-clickRoot]
//            Element first = document.select("div[id=layoutPage]").first();
//
//            System.out.println(first.getElementById("div[class=class=w8m]").text());
//
//
////            String priceOldString
////                    = element.select("span[class=mute--text text--lineThrough size--all-l size--fromW3-xl]").text();
//
//
//
//
//
//
//
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//}
