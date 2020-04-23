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
        setContentView(R.layout.activity_main_new_version);

    }


    public void changeToSearchView(View view){
        Intent intent = new Intent(this, ChooseTypeActivity.class);
        startActivity(intent);
    }

    public void changeToFavouritesView(View view){
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    public void changeToSearchOccasionsView(View view){
        Intent intent = new Intent(this, SearchOccasionsActivity.class);
        startActivity(intent);
    }

    public void logOut(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.data), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.username));
        editor.remove(getString(R.string.token));
        editor.apply();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
