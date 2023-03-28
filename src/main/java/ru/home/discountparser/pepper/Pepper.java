package ru.home.discountparser.pepper;

import java.time.LocalDate;
import java.util.Objects;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Scope("prototype")
public class Pepper {

    private String priceOld;
    private String priceNew;
    private String percentDiscount;
    private String productDescription;
    private String description;
    private String imageUrl;
    private String url;
    private boolean newPost = true;
    private LocalDate date = LocalDate.now();

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

    public void setNewPost(boolean newPost) {
        this.newPost = newPost;
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
                    description, imageUrl, url, newPost);
        }
    }
}
