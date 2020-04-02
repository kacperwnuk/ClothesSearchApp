package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.SortingType;
import com.example.clothessearchapp.adapters.ClothesRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.DetailClothesRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClothesGeneralListingActivity extends AppCompatActivity {


    private List<Clothes> clothes;
    private ClothesRecyclerAdapter adapter;
    private ProgressDialog progressDialog;

    private class ClothesFilter {
        private String type;
        private String color;
        private String size;
        private String lowerPrice;
        private String higherPrice;
        private SortingType sortingType;

        //        private Boolean ascending;
        ClothesFilter(String type, String color, String size, String lowerPrice, String higherPrice, SortingType sortingType) {
            this.type = type;
            this.color = color;
            this.size = size;
            this.lowerPrice = lowerPrice;
            this.higherPrice = higherPrice;
            this.sortingType = sortingType;
        }

        ClothesFilter(Intent intent){
            type = intent.getStringExtra("type");
            lowerPrice = intent.getStringExtra("lowerPrice");
            higherPrice = intent.getStringExtra("higherPrice");
            color = intent.getStringExtra("color");
            size = intent.getStringExtra("size");
            sortingType = SortingType.valueOf(intent.getStringExtra("sortingType"));

        }

//        List<OldClothes> getFilteredClothes(List<OldClothes> clothes) {
//            List<OldClothes> filteredClothes = clothes;
//
//            if (!color.equals("")) {
//                filteredClothes = filteredClothes.stream().filter(c -> c.getColor().equals(color)).collect(Collectors.toList());
//            }
//
//            if (!size.equals("")){
//                filteredClothes = filteredClothes.stream().filter(c -> c.getSize().equals(size)).collect(Collectors.toList());
//            }
//
//            filteredClothes = filteredClothes.stream().filter(c -> c.getPrice() >= lowerPrice && c.getPrice() <= higherPrice).collect(Collectors.toList());
//
//            switch (sortingType){
//                case ASCENDING:
//                    filteredClothes.sort(Comparator.comparing(OldClothes::getPrice));
//                case DESCENDING:
//                    filteredClothes.sort(Comparator.comparing(OldClothes::getPrice).reversed());
//            }
//
//            return filteredClothes;
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_general_listing);
        Intent intent = getIntent();
        ClothesFilter clothesFilter = new ClothesFilter(intent);

//        Toast.makeText(this, lowerPrice + Integer.toString(higherPrice) + color + size, Toast.LENGTH_LONG).show();

//        ClothesFilter clothesFilter = new ClothesFilter(color, size, lowerPrice, higherPrice, sortingType);
//        List<OldClothes> filteredClothes = clothesFilter.getFilteredClothes(clothes);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Looking for clothes...");
        progressDialog.show();
        getFilteredClothes(clothesFilter);


    }

    public void showDetail(View view){
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        DetailClothesRequest detailClothesRequest = (DetailClothesRequest) view.getTag();
        intent.putExtra("request", detailClothesRequest);
        System.out.println(view.getTag());
        startActivity(intent);
    }

    private void getFilteredClothes(ClothesFilter clothesFilter) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        Call<List<Clothes>> call = service.getClothes(clothesFilter.type, clothesFilter.color, clothesFilter.size, clothesFilter.lowerPrice, clothesFilter.higherPrice, clothesFilter.sortingType.name().toLowerCase());
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                progressDialog.dismiss();
                clothes = response.body();

                adapter = new ClothesRecyclerAdapter(clothes);
                RecyclerView recycler = findViewById(R.id.clothes_recycler_view);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new GridLayoutManager(ClothesGeneralListingActivity.this, 1));
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });

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
