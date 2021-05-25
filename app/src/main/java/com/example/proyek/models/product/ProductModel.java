package com.example.proyek.models.product;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel {
    // Variable
    private String name, price, url;

    // Constructor
    public ProductModel(){

    }

    public ProductModel(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    // Setter dan Getter
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
