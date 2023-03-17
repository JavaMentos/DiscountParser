//package ru.home.discountparser.ozon;
//
//import java.util.Objects;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//@Component
//@Scope("prototype")
//public class Ozon {
//    private String name;
//    private String price;
//
//    public Ozon(String name, String price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Ozon ozon = (Ozon) o;
//        return Objects.equals(name, ozon.name) && Objects.equals(price, ozon.price);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, price);
//    }
//
//    @Override
//    public String toString() {
//        return "Ozon{" +
//                "name='" + name + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }
//}
