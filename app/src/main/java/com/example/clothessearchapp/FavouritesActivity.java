package com.example.clothessearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private List<Clothes> clothes = new ArrayList<>(Arrays.asList(
            new Clothes(1, "T-Shirt", "Czerwony", "M", 50, "Wzorzysta bluzka", false),
            new Clothes(2, "Kurtka", "Niebieski", "S", 30, "Jesienna kurtka", true),
            new Clothes(3,"Spodnie", "Czarny", "L", 75, "Spodnie chino", false),
            new Clothes(4,"T-Shirt", "Różowy", "XL", 100, "Różowy T-Shirt", true),
            new Clothes(5,"T-Shirt", "Pomarańczowy", "XL", 125, "T-Shirt w plamy", false),
            new Clothes(6,"T-Shirt", "Czarny", "XL", 20, "T-Shirt w kropki", false),
            new Clothes(7,"T-Shirt", "Czerwony", "XL", 40, "T-Shirt w paski", false),
            new Clothes(8,"T-Shirt", "Różowy", "M", 45, "Różowy T-Shirt", false),
            new Clothes(9,"T-Shirt", "Różowy", "L", 45, "Różowy T-Shirt", false)
    ));
    private FavouriteClothesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        adapter = new FavouriteClothesRecyclerAdapter(clothes);
        RecyclerView recyclerView = findViewById(R.id.favourites_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showDetail(View view){
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        Clothes chosenClothes = clothes.stream().filter(c -> c.getId() == view.getId()).findFirst().orElse(null);
        if(chosenClothes != null){
            intent.putExtra("chosenClothes", chosenClothes);
            startActivity(intent);
        }
    }

    public void deleteClothes(View view){
        Clothes clothesToRemove = clothes.stream().filter(c -> c.getId() == view.getId()).findFirst().orElse(null);
        if(clothesToRemove != null){
            clothes.remove(clothesToRemove);
            adapter.notifyDataSetChanged();
        }
    }


}
