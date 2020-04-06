package com.example.clothessearchapp.structure;

public class Occasion {

    private String key;
    private String type;
    private String color;
    private String size;
    private String price;

    public Occasion(String key, String type, String color, String size, String price) {
        this.key = key;
        this.type = type;
        this.color = color;
        this.size = size;
        this.price = price;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
