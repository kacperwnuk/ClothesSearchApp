package com.example.clothessearchapp.structure;

import java.util.List;

public class Type {
    private String name;
    private List<String> colors;

    public Type() {
        this.name = "";
        this.colors = null;
    }
    public Type(String name, List<String> colors) {
        this.name = name;
        this.colors = colors;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
