package com.example.clothessearchapp.structure;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailClothesRequest implements Parcelable {
    private String shop;
    private String key;

    public DetailClothesRequest(String shop, String key) {
        this.shop = shop;
        this.key = key;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop);
        dest.writeString(this.key);
    }

    protected DetailClothesRequest(Parcel in) {
        this.shop = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<DetailClothesRequest> CREATOR = new Parcelable.Creator<DetailClothesRequest>() {
        @Override
        public DetailClothesRequest createFromParcel(Parcel source) {
            return new DetailClothesRequest(source);
        }

        @Override
        public DetailClothesRequest[] newArray(int size) {
            return new DetailClothesRequest[size];
        }
    };
}
