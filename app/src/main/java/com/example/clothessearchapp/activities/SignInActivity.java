package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.adapters.ViewPagerAdapter;
import com.example.clothessearchapp.fragments.SignUpFragment;
import com.example.clothessearchapp.fragments.SignInFragment;
import com.example.clothessearchapp.materialux.ViewPagerNoSwipe;
import com.google.android.material.tabs.TabLayout;

public class SignInActivity extends AppCompatActivity {

//Material UXUI code
    ViewPagerNoSwipe viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.data), 0);
        String token = sharedPreferences.getString(getString(R.string.token), "");

        if (!token.equals("")){
            goToMenu();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        viewPager = findViewById(R.id.pager);

        //  disable swiping
        viewPager.setSwipeEnabled(false);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new SignUpFragment(), "Join Now");
        adapter.addFragment(new SignInFragment(), "Sign In");
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
