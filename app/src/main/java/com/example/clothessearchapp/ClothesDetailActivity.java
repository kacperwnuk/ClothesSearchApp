package com.example.clothessearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;



public class ClothesDetailActivity extends AppCompatActivity {


    private TextView clothesName;
    private TextView clothesType;
    private TextView clothesPrice;
    private TextView clothesLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);

        getDisplayContent();

        Bundle data = getIntent().getExtras();
        if(data != null){
            Clothes clothes = data.getParcelable("chosenClothes");
            setDisplayContent(clothes);
        }


    }

    private void setDisplayContent(Clothes clothes) {
        clothesName.setText(clothes.getName());
        clothesType.setText(clothes.getType());
        clothesPrice.setText(clothes.getPrice().toString());
    }

    private void getDisplayContent() {
        clothesName = findViewById(R.id.clothes_name);
        clothesType = findViewById(R.id.clothes_type);
        clothesPrice = findViewById(R.id.clothes_price);
        clothesLink = findViewById(R.id.clothes_link);
    }

}
