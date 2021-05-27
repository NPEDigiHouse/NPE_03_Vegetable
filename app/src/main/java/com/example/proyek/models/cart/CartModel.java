package com.example.proyek.models.cart;

public class CartModel {

    private String name, price, url, total_order, total_price, instructions, child_id;

    public CartModel() {
    }

    public CartModel(String name, String price, String url, String total_order, String total_price, String instructions, String child_id) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.total_order = total_order;
        this.total_price = total_price;
        this.instructions = instructions;
        this.child_id = child_id;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
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

    public String getTotal_order() {
        return total_order;
    }

    public void setTotal_order(String total_order) {
        this.total_order = total_order;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
