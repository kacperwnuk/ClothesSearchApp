package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.adapters.ClothesRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.DetailClothesRequest;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity {

    private List<Clothes> clothes;
    private ProgressDialog progressDialog;
    private String token;

    private ClothesRecyclerAdapter adapter;

    private GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_general_listing);

        token = getToken();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wczytywanie ulubionych ciuchów...");
        progressDialog.show();

        getFavouriteClothes();

    }

    private void getFavouriteClothes() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        Call<List<Clothes>> call = service.getFavourites("Token " + token);
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                progressDialog.dismiss();
                clothes = response.body();

                adapter = new ClothesRecyclerAdapter(clothes, R.layout.favourite_clothes_card_view_item);
                RecyclerView recyclerView = findViewById(R.id.clothes_recycler_view);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(FavouritesActivity.this,1 ));

            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });

    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.data), 0);
        return sharedPreferences.getString(getString(R.string.token), "");
    }

    public void showDetail(View view){
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        DetailClothesRequest detailClothesRequest = (DetailClothesRequest) view.getTag();
        intent.putExtra("request", detailClothesRequest);
        System.out.println(view.getTag());
        startActivity(intent);
    }

    public void deleteClothes(View view){
        DetailClothesRequest detailClothesRequest = (DetailClothesRequest) view.getTag();
        System.out.println(detailClothesRequest.getKey());

        Optional<Clothes> clothesToRemove = clothes.stream().filter(c -> c.getKey().equals(detailClothesRequest.getKey())).findFirst();
        if (clothesToRemove.isPresent()){
            clothes.remove(clothesToRemove.get());
            deleteFavourite(detailClothesRequest);
            adapter.notifyDataSetChanged();
        }

    }

    private void deleteFavourite(DetailClothesRequest detailClothesRequest) {

        Call<Clothes> callFavourite = service.deleteFavourite("Token " + token, detailClothesRequest.getKey());

        callFavourite.enqueue(new Callback<Clothes>() {
            @Override
            public void onResponse(Call<Clothes> call, Response<Clothes> response) {

            }

            @Override
            public void onFailure(Call<Clothes> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favourites_menu, menu);
        return true;
    }

    public boolean deleteFavourites(MenuItem item){

        clothes.clear();
        adapter.notifyDataSetChanged();

        Call<Clothes> callFavourite = service.deleteFavourite("Token " + token, "all");
        callFavourite.enqueue(new Callback<Clothes>() {
            @Override
            public void onResponse(Call<Clothes> call, Response<Clothes> response) {

            }

            @Override
            public void onFailure(Call<Clothes> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        return true;
    }

    public boolean sendEmail(MenuItem item){

        Snackbar.make(findViewById(R.id.constraint_layout), "Wysłano ulubione na maila", Snackbar.LENGTH_LONG).show();
        Call<String> callMail = service.sendEmail("Token " + token);

        callMail.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return true;
    }

}
