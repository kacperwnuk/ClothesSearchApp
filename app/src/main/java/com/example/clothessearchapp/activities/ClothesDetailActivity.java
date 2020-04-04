package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clothessearchapp.R;
import com.example.clothessearchapp.adapters.ViewPagerAdapter;
import com.example.clothessearchapp.fragments.DescriptionFragment;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.DetailClothesRequest;
import com.example.clothessearchapp.structure.DetailedClothes;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClothesDetailActivity extends AppCompatActivity {

    private String token;

    private ProgressDialog progressDialog;
    private DetailedClothes clothes;

    private ImageView clothesImg;
    private TextView clothesName;
    private TextView clothesPrice;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView favouriteStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);

        token = getToken();
        getDisplayContent();

        Bundle data = getIntent().getExtras();
        if (data != null) {
            DetailClothesRequest request = data.getParcelable("request");
            getClothesInfo(request);

        }
    }

    private void getClothesInfo(DetailClothesRequest request) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing clothes details...");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);


        Call<DetailedClothes> call = service.getDetailedClothes("Token " + token, request.getKey(), request.getShop());
        call.enqueue(new Callback<DetailedClothes>() {
            @Override
            public void onResponse(Call<DetailedClothes> call, Response<DetailedClothes> response) {
                clothes = response.body();
                clothes.setFavourite(false);
                checkFavourite(clothes);
            }

            @Override
            public void onFailure(Call<DetailedClothes> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });


    }

    private void checkFavourite(DetailedClothes cl) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        Call<List<Clothes>> callFavourite = service.getFavourites("Token " + token);
        callFavourite.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                progressDialog.dismiss();
                List<Clothes> favClothes = response.body();
                if (favClothes != null && favClothes.stream().anyMatch(c -> c.getKey().equals(cl.getKey()))) {
                    clothes.setFavourite(true);
                } else {
                    clothes.setFavourite(false);
                }
                setDisplayContent(clothes);
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setDisplayContent(DetailedClothes clothes) {
        clothesName.setText(clothes.getName());
        clothesPrice.setText(clothes.getPrice());
        String imgLink = clothes.getImageLink();

        Glide.with(this).load(imgLink).into(clothesImg);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        addFragments(clothes, viewPagerAdapter);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (clothes.isFavourite()) {
            favouriteStar.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            favouriteStar.setImageResource(android.R.drawable.btn_star_big_off);
        }

        favouriteStar.setOnClickListener(v -> {

            if (clothes.isFavourite()) {
                favouriteStar.setImageResource(android.R.drawable.btn_star_big_off);
                changeFavourite(clothes);
                clothes.setFavourite(false);
            } else {
                favouriteStar.setImageResource(android.R.drawable.btn_star_big_on);
                changeFavourite(clothes);
                clothes.setFavourite(true);
            }
        });
    }

    private void changeFavourite(DetailedClothes clothes) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        Call<Clothes> callFavourite;

        if (clothes.isFavourite()) {
            callFavourite = service.deleteFavourite("Token " + token, clothes.getKey());
        } else {
            callFavourite = service.addFavourite("Token " + token, clothes.getKey());

        }

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


    private void addFragments(DetailedClothes detailedClothes, ViewPagerAdapter viewPagerAdapter) {
        String description = detailedClothes.getDescription();
        String sizes = detailedClothes.sizesRepresentation();
        String colors = detailedClothes.colorsRepresentation();

        viewPagerAdapter.addFragment(new DescriptionFragment(description), "Opis");
        viewPagerAdapter.addFragment(new DescriptionFragment(sizes), "Rozmiary");
        viewPagerAdapter.addFragment(new DescriptionFragment(colors), "Kolory");
    }

    private void getDisplayContent() {
        clothesName = findViewById(R.id.clothes_name);
        clothesPrice = findViewById(R.id.clothes_price);
        clothesImg = findViewById(R.id.clothes_img);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        favouriteStar = findViewById(R.id.favourite_star);
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", 0);
        return sharedPreferences.getString(getString(R.string.token), "");
    }
}
