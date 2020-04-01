package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.OldClothes;

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
            OldClothes clothes = data.getParcelable("chosenClothes");
            setDisplayContent(clothes);
        }


    }

    private void setDisplayContent(OldClothes clothes) {
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
