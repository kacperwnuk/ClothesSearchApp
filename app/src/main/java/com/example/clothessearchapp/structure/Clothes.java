package com.example.clothessearchapp.structure;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Clothes {

    @SerializedName("key")
    private String key;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("img_link")
    private String imageLink;
    @SerializedName("colors")
    private List<String> colors;
    @SerializedName("sizes")
    private List<String> sizes;
    private Boolean favourite;

    public Clothes(String key, String type, String name, String price, String imageLink, List<String> colors, List<String> sizes, Boolean favourite) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.price = price;
        this.imageLink = imageLink;
        this.colors = colors;
        this.sizes = sizes;
        this.favourite = favourite;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return  Objects.equals(key, clothes.key) &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(name, clothes.name) &&
                Objects.equals(price, clothes.price) &&
                Objects.equals(imageLink, clothes.imageLink) &&
                Objects.equals(colors, clothes.colors) &&
                Objects.equals(sizes, clothes.sizes) &&
                Objects.equals(favourite, clothes.favourite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type, name, price, imageLink, colors, sizes, favourite);
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(this.id);
//        dest.writeString(this.key);
//        dest.writeString(this.type.getName());
//        dest.writeString(this.name);
//        dest.writeValue(this.price);
//        dest.writeSerializable(this.imageLink);
//        dest.writeList(this.colors);
//        dest.writeList(this.sizes);
//        dest.writeValue(this.favourite);
//    }
//
//    protected Clothes(Parcel in) {
//        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
//        this.key = in.readString();
//        this.type = Type(in.readString());
//        this.name = in.readString();
//        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
//        this.imageLink = (URL) in.readSerializable();
//        this.colors = new ArrayList<Color>();
//        in.readList(this.colors, Color.class.getClassLoader());
//        this.sizes = new ArrayList<Size>();
//        in.readList(this.sizes, Size.class.getClassLoader());
//        this.favourite = (Boolean) in.readValue(Boolean.class.getClassLoader());
//    }
//
//    public static final Creator<Clothes> CREATOR = new Creator<Clothes>() {
//        @Override
//        public Clothes createFromParcel(Parcel source) {
//            return new Clothes(source);
//        }
//
//        @Override
//        public Clothes[] newArray(int size) {
//            return new Clothes[size];
//        }
//    };
}
