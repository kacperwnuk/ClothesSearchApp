package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clothessearchapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Request example
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
////        Call<List<Clothes>> call = service.getClothes("T-SHIRT", "","","","","");
////        call.enqueue(new Callback<List<Clothes>>() {
////            @Override
////            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
////                System.out.println(response.body());
////            }
////
////            @Override
////            public void onFailure(Call<List<Clothes>> call, Throwable t) {
////                System.out.println(t.getMessage());
////            }
////        });
    }


    public void changeToSearchView(View view){
        Intent intent = new Intent(this, ChooseTypeActivity.class);
        startActivity(intent);
    }

    public void changeToFavouritesView(View view){
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    public void changeToFiltersView(View view){
        Intent intent = new Intent(this, SearchTypeActivity.class);
        startActivity(intent);
    }

    public void changeToSearchOccasionsView(View view){
        Intent intent = new Intent(this, SearchOccasionsActivity.class);
        startActivity(intent);
    }
}
