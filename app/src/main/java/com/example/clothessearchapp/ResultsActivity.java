package com.example.clothessearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.OldClothes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsActivity extends AppCompatActivity {

    private class ClothesFilter {

        private String color;
        private String size;
        private Integer lowerPrice;
        private Integer higherPrice;
        private SortingType sortingType;

        //        private Boolean ascending;
        ClothesFilter(String color, String size, Integer lowerPrice, Integer higherPrice, SortingType sortingType) {
            this.color = color;
            this.size = size;
            this.lowerPrice = lowerPrice;
            this.higherPrice = higherPrice;
            this.sortingType = sortingType;
        }

        List<OldClothes> getFilteredClothes(List<OldClothes> clothes) {
            List<OldClothes> filteredClothes = clothes;

            if (!color.equals("")) {
                filteredClothes = filteredClothes.stream().filter(c -> c.getColor().equals(color)).collect(Collectors.toList());
            }

            if (!size.equals("")){
                filteredClothes = filteredClothes.stream().filter(c -> c.getSize().equals(size)).collect(Collectors.toList());
            }

            filteredClothes = filteredClothes.stream().filter(c -> c.getPrice() >= lowerPrice && c.getPrice() <= higherPrice).collect(Collectors.toList());

            switch (sortingType){
                case ASCENDING:
                    filteredClothes.sort(Comparator.comparing(OldClothes::getPrice));
                case DESCENDING:
                    filteredClothes.sort(Comparator.comparing(OldClothes::getPrice).reversed());
            }

            return filteredClothes;
        }
    }

    private List<OldClothes> clothes = new ArrayList<>(Arrays.asList(
            new OldClothes(1, "T-Shirt", "Czerwony", "M", 50, "Wzorzysta bluzka", false),
            new OldClothes(2,"Kurtka", "Niebieski", "S", 30, "Jesienna kurtka", true),
            new OldClothes(3,"Spodnie", "Czarny", "L", 75, "Spodnie chino", false),
            new OldClothes(4,"T-Shirt", "Różowy", "XL", 100, "Różowy T-Shirt", true),
            new OldClothes(5,"T-Shirt", "Pomarańczowy", "XL", 125, "T-Shirt w plamy", false),
            new OldClothes(6,"T-Shirt", "Czarny", "XL", 20, "T-Shirt w kropki", false),
            new OldClothes(7,"T-Shirt", "Czerwony", "XL", 40, "T-Shirt w paski", false),
            new OldClothes(8,"T-Shirt", "Różowy", "M", 45, "Różowy T-Shirt", false),
            new OldClothes(9,"T-Shirt", "Różowy", "L", 45, "Różowy T-Shirt", false)
    ));

    private ClothesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        int lowerPrice = intent.getIntExtra("lowerPrice", 0);
        int higherPrice = intent.getIntExtra("higherPrice", 1000);
        String color = intent.getStringExtra("color");
        String size = intent.getStringExtra("size");
        SortingType sortingType = SortingType.valueOf(intent.getStringExtra("sortingType"));
//        Toast.makeText(this, lowerPrice + Integer.toString(higherPrice) + color + size, Toast.LENGTH_LONG).show();

        ClothesFilter clothesFilter = new ClothesFilter(color, size, lowerPrice, higherPrice, sortingType);
        List<OldClothes> filteredClothes = clothesFilter.getFilteredClothes(clothes);

        adapter = new ClothesRecyclerAdapter(filteredClothes);
        RecyclerView recycler = findViewById(R.id.clothes_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
