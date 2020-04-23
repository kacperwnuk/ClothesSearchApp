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
    @SerializedName("shop")
    private String shop;

    private Boolean favourite = false;

    public Clothes(String key, String type, String name, String price, String imageLink, List<String> colors, List<String> sizes, String shop) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.price = price;
        this.imageLink = imageLink;
        this.colors = colors;
        this.sizes = sizes;
        this.shop = shop;
    }

    public String getShop() { return shop;}

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

    public Boolean isFavourite() {
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


    public String sizesRepresentation(){
        StringBuilder result = new StringBuilder();
        for(String size : sizes){
            result.append(String.format("%s \n", size));
        }
        return result.toString();
    }

    public String colorsRepresentation(){
        StringBuilder result = new StringBuilder();
        for(String color : colors){
            result.append(String.format("%s \n", color));
        }
        return result.toString();
    }


}
