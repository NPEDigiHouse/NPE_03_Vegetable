package com.example.proyek.models.product;

public class ProductModel {
    private String name, price, url;

    public ProductModel(){

    }

    public ProductModel(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
