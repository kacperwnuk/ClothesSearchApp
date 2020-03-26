package com.example.clothessearchapp.structure;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class OldClothes implements Parcelable {

    private Integer id;
    private String type;
    private String color;
    private String size;
    private Integer price;
    private String name;
    private Boolean favourite;

    public OldClothes(Integer id, String type, String color, String size, Integer price, String name, Boolean favourite) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.size = size;
        this.price = price;
        this.name = name;
        this.favourite = favourite;
    }


    public Integer getId() {
        return id;
    }


    public Boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }


    public String getName(){
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OldClothes{" +
                "id='" + id + '\'' +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldClothes clothes = (OldClothes) o;
        return Objects.equals(type, clothes.type) &&
                Objects.equals(color, clothes.color) &&
                Objects.equals(size, clothes.size) &&
                Objects.equals(price, clothes.price) &&
                Objects.equals(id, clothes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, color, size, price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.type);
        dest.writeString(this.color);
        dest.writeString(this.size);
        dest.writeValue(this.price);
        dest.writeString(this.name);
        dest.writeValue(this.favourite);
    }

    protected OldClothes(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.color = in.readString();
        this.size = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.favourite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OldClothes> CREATOR = new Parcelable.Creator<OldClothes>() {
        @Override
        public OldClothes createFromParcel(Parcel source) {
            return new OldClothes(source);
        }

        @Override
        public OldClothes[] newArray(int size) {
            return new OldClothes[size];
        }
    };
}
