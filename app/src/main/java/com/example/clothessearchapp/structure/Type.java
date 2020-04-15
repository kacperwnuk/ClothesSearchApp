package com.example.clothessearchapp.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Type {
    private String name;
    private List<String> colors;

    public static Map<String, String> polishNames = new HashMap<String, String>() {{
        put("T-SHIRT", "T-shirty");
        put("SHIRT", "Koszule");
        put("JACKET", "Marynarki");
        put("SHORTS", "Szorty");
        put("PANTS", "Spodnie");
        put("SWEATER", "Swetry");
    }};

    public static Map<String, String> englishNames = new HashMap<String, String>() {{
        put("T-shirty", "T-SHIRT");
        put("Koszule", "SHIRT");
        put("Marynarki", "JACKET");
        put("Szorty", "SHORTS");
        put("Spodnie", "PANTS");
        put("Swetry", "SWEATER");
    }};


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

    public String getPolishName(){
        return polishNames.getOrDefault(this.name, "Ubrania");
    }
}
