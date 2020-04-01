package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clothessearchapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Data", 0);
//        String token = sharedPreferences.getString("Token", "");
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void changeToSearchOccasionsView(View view){
        Intent intent = new Intent(this, SearchOccasionsActivity.class);
        startActivity(intent);
    }

    public void logOut(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.data), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.token));
        editor.apply();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
