package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
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
import com.example.clothessearchapp.structure.DetailClothesRequest;
import com.example.clothessearchapp.structure.DetailedClothes;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClothesDetailActivity extends AppCompatActivity {

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

        getDisplayContent();

        Bundle data = getIntent().getExtras();
        if(data != null){
            DetailClothesRequest request = data.getParcelable("request");
            getClothesInfo(request);

            //
        }


    }

    private void getClothesInfo(DetailClothesRequest request) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing clothes details...");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        Call<DetailedClothes> call = service.getDetailedClothes(request.getKey(), request.getShop());
        call.enqueue(new Callback<DetailedClothes>() {
            @Override
            public void onResponse(Call<DetailedClothes> call, Response<DetailedClothes> response) {
                progressDialog.dismiss();
                clothes = response.body();
                setDisplayContent(clothes);
            }

            @Override
            public void onFailure(Call<DetailedClothes> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });


    }

    private void setDisplayContent(DetailedClothes clothes) {
        clothesName.setText(clothes.getName());
        clothesPrice.setText(clothes.getPrice());
        String imgLink = "http://lp2.hm.com/hmgoepprod?set=source[/37/58/3758bc3ecb6516cdee8441b7b51115ea14091bd0.jpg],origin[dam],category[men_tshirtstanks_shortsleeve],type[DESCRIPTIVESTILLLIFE],res[m],hmver[1]&call=url[file:/product/style]";

        Glide.with(this).load(imgLink).into(clothesImg);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        addFragments(clothes, viewPagerAdapter);


//        viewPagerAdapter.addFragment();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

       favouriteStar.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Zmiana", Toast.LENGTH_SHORT).show();

            if(clothes.isFavourite()){
                favouriteStar.setImageResource(android.R.drawable.btn_star_big_off);
                clothes.setFavourite(false);
            } else {
                favouriteStar.setImageResource(android.R.drawable.btn_star_big_on);
                clothes.setFavourite(true);
            }
        });
    }

    private void addFragments(DetailedClothes detailedClothes,ViewPagerAdapter viewPagerAdapter) {
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

}
