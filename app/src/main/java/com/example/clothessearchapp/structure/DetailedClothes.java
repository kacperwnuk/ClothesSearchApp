package com.example.clothessearchapp.structure;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

public class DetailedClothes extends Clothes {
    @SerializedName("description")
    private String description;
    @SerializedName("composition")
    private String composition;

    public DetailedClothes(String key, String type, String name, String price, String imageLink, List<String> colors, List<String> sizes, String shop, String description, String composition) {
        super(key, type, name, price, imageLink, colors, sizes, shop);
        this.description = description;
        this.composition = composition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

}
