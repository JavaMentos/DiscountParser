package ru.home.discountparser.pepper;

import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Pepper {


    private final String priceOld;
    private final String priceNew;
    private final String percentDiscount;
    private final String productDescription;
    private final String description;
    private final String imageUrl;
    private final String url;
    private boolean newPost;

    public Pepper(final String priceOld,
                  final String priceNew,
                  final String percentDiscount,
                  final String productDescription,
                  final String description,
                  final String imageUrl,
                  final String url,
                  boolean newPost) {
        this.priceOld = priceOld;
        this.priceNew = priceNew;
        this.percentDiscount = percentDiscount;
        this.productDescription = productDescription;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
        this.newPost = newPost;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public String getPriceNew() {
        return priceNew;
    }

    public String getPercentDiscount() {
        return percentDiscount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public boolean isNewPost() {
        return newPost;
    }

    public void setNewPost(boolean newPost) {
        this.newPost = newPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pepper pepper = (Pepper) o;
        return newPost == pepper.newPost && Objects.equals(priceOld, pepper.priceOld) && Objects.equals(priceNew, pepper.priceNew) && Objects.equals(percentDiscount, pepper.percentDiscount) && Objects.equals(productDescription, pepper.productDescription) && Objects.equals(description, pepper.description) && Objects.equals(imageUrl, pepper.imageUrl) && Objects.equals(url, pepper.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceOld, priceNew, percentDiscount, productDescription, description, imageUrl, url, newPost);
    }

    @Override
    public String toString() {
        return "Pepper{" +
                "priceOld='" + priceOld + '\'' +
                ", priceNew='" + priceNew + '\'' +
                ", percentDiscount='" + percentDiscount + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", newPost=" + newPost +
                '}';
    }

    public static class Builder {
        private String priceOld;
        private String priceNew;
        private String percentDiscount;
        private String productDescription;
        private String description;
        private String imageUrl;
        private String url;
        private boolean newPost;

        public Builder setPriceOld(String priceOld) {
            this.priceOld = priceOld;
            return this;
        }

        public Builder setPriceNew(String priceNew) {
            this.priceNew = priceNew;
            return this;
        }

        public Builder setProductDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setNewPost(boolean newPost) {
            this.newPost = newPost;
            return this;
        }

        public Builder setPercentDiscount(String percentDiscount) {
            this.percentDiscount = percentDiscount;
            return this;
        }

        public Pepper build() {
            return new Pepper(
                    priceOld, priceNew, percentDiscount, productDescription,
                    description, imageUrl, url,newPost);
        }
    }
}
