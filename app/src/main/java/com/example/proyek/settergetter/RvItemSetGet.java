package com.example.proyek.settergetter;

import android.os.Parcel;
import android.os.Parcelable;

public class RvItemSetGet implements Parcelable {

    //bagian ini sama dengan bagian modal, akan tetapi menggunakan parcelable karena menggunakan data static dan agar lebih efisien
    //deklarasi variabel
    private int photo;
    private String name;

    public RvItemSetGet(){

    }

    protected RvItemSetGet(Parcel in) {
        photo = in.readInt();
        name = in.readString();
    }

    public static final Creator<RvItemSetGet> CREATOR = new Creator<RvItemSetGet>() {
        @Override
        public RvItemSetGet createFromParcel(Parcel in) {
            return new RvItemSetGet(in);
        }

        @Override
        public RvItemSetGet[] newArray(int size) {
            return new RvItemSetGet[size];
        }
    };

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photo);
        parcel.writeString(name);
    }
}
