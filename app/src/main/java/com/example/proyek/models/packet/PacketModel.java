package com.example.proyek.models.packet;

public class PacketModel {
    
    //deklarasi variabel private
    private String name, price, url;

    //membuat constructor kosong
    public PacketModel() {
    }

    //membuat constructor
    public PacketModel(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    //membuat setter dan getter untuk variabel
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
}
