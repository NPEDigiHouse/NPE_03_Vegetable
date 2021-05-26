package com.example.proyek.models.fav;

public class FavModel {
    private String name, price, url, child_id;

    public FavModel() {
    }

    public FavModel(String name, String price, String url, String child_id) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.child_id = child_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }
}
