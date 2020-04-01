package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.adapters.FavouriteClothesRecyclerAdapter;
import com.example.clothessearchapp.structure.OldClothes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private List<OldClothes> clothes = new ArrayList<>(Arrays.asList(
            new OldClothes(1, "T-Shirt", "Czerwony", "M", 50, "Wzorzysta bluzka", false),
            new OldClothes(2, "Kurtka", "Niebieski", "S", 30, "Jesienna kurtka", true),
            new OldClothes(3,"Spodnie", "Czarny", "L", 75, "Spodnie chino", false),
            new OldClothes(4,"T-Shirt", "Różowy", "XL", 100, "Różowy T-Shirt", true),
            new OldClothes(5,"T-Shirt", "Pomarańczowy", "XL", 125, "T-Shirt w plamy", false),
            new OldClothes(6,"T-Shirt", "Czarny", "XL", 20, "T-Shirt w kropki", false),
            new OldClothes(7,"T-Shirt", "Czerwony", "XL", 40, "T-Shirt w paski", false),
            new OldClothes(8,"T-Shirt", "Różowy", "M", 45, "Różowy T-Shirt", false),
            new OldClothes(9,"T-Shirt", "Różowy", "L", 45, "Różowy T-Shirt", false)
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
        OldClothes chosenClothes = clothes.stream().filter(c -> c.getId() == view.getId()).findFirst().orElse(null);
        if(chosenClothes != null){
            intent.putExtra("chosenClothes", chosenClothes);
            startActivity(intent);
        }
    }

    public void deleteClothes(View view){
        OldClothes clothesToRemove = clothes.stream().filter(c -> c.getId() == view.getId()).findFirst().orElse(null);
        if(clothesToRemove != null){
            clothes.remove(clothesToRemove);
            adapter.notifyDataSetChanged();
        }
    }


}
