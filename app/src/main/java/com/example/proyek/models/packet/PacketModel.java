package com.example.proyek.models.packet;

import android.os.Parcel;
import android.os.Parcelable;

public class PacketModel implements Parcelable {
    private String name, price, url;

    public PacketModel(){

    }

    public PacketModel(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    protected PacketModel(Parcel in) {
        name = in.readString();
        price = in.readString();
        url = in.readString();
    }


    public static final Creator<PacketModel> CREATOR = new Creator<PacketModel>() {
        @Override
        public PacketModel createFromParcel(Parcel in) {
            return new PacketModel(in);
        }

        @Override
        public PacketModel[] newArray(int size) {
            return new PacketModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(url);
    }
}
