package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.SortingType;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Color;
import com.example.clothessearchapp.structure.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String typeName;

//    private List<Color> colors;
//    private List<Size> sizes;

    private List<String> colors;
    private List<String> sizes;

    private ArrayAdapter<String> colorAdapter;
    private ArrayAdapter<String> sizeAdapter;

    private boolean colourChecked = false;
    private boolean sizeChecked = false;

    private Spinner colourDropdown;
    private Spinner sizeDropdown;
    private TextView lowerPrice;
    private TextView higherPrice;
    private CheckBox ascendingCheckbox;
    private CheckBox descendingCheckbox;
    private Button submit;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        typeName = getIntent().getStringExtra("clothesType");
        colors = new ArrayList<>();
        sizes = new ArrayList<>();

        submit = findViewById(R.id.submit_filter);
        submit.setEnabled(false);

        lowerPrice = findViewById(R.id.lower_price);
        higherPrice = findViewById(R.id.higher_price);

        ascendingCheckbox = findViewById(R.id.ascending_cb);
        descendingCheckbox = findViewById(R.id.descending_cb);

        ascendingCheckbox.setOnClickListener(v -> {
            if (ascendingCheckbox.isChecked()) {
                descendingCheckbox.setChecked(false);
            }
        });

        descendingCheckbox.setOnClickListener(v -> {
            if (descendingCheckbox.isChecked()) {
                ascendingCheckbox.setChecked(false);
            }
        });

        initializeDropdowns();

        loadDataFromServer();

    }

    private void loadDataFromServer() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", 0);
        String token = sharedPreferences.getString(getString(R.string.token), "");


        Call<List<Color>> call = service.getColors("Token " + token, this.typeName);

        call.enqueue(new Callback<List<Color>>() {
            @Override
            public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                List<Color> cls = response.body();
                colors.clear();
                colors.addAll(cls.stream().map(Color::getName).collect(Collectors.toList()));
                colorAdapter.notifyDataSetChanged();
                enableButton();
            }

            @Override
            public void onFailure(Call<List<Color>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        Call<List<Size>> call2 = service.getSizes("Token " + token);
        call2.enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                List<Size> sizeList = response.body();
                sizes.clear();
                sizes.addAll(sizeList.stream().map(Size::getName).collect(Collectors.toList()));
                sizeAdapter.notifyDataSetChanged();
                enableButton();
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void enableButton() {
        if(colors.size() != 0 && sizes.size() != 0){
            submit.setEnabled(true);
        }
    }


    private void initializeDropdowns() {
        colourDropdown = findViewById(R.id.color_spinner);
        colourDropdown.setOnItemSelectedListener(this);
        sizeDropdown = findViewById(R.id.size_spinner);
        sizeDropdown.setOnItemSelectedListener(this);

        colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);

        colourDropdown.setAdapter(colorAdapter);
        colourDropdown.setSelection(0);
        sizeDropdown.setAdapter(sizeAdapter);
        sizeDropdown.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent == colourDropdown) {
            if (!colourChecked) {
                colourChecked = true;
            } else {
//                Toast.makeText(view.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                colourDropdown.setSelection(position);
            }
        } else {
            if (!sizeChecked) {
                sizeChecked = true;
            } else {
//                Toast.makeText(view.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                sizeDropdown.setSelection(position);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submitForm(View view) {
//        String text = lowerPrice.getText() + " " + higherPrice.getText() + " " + colourDropdown.getSelectedItem().toString() + " " + sizeDropdown.getSelectedItem().toString();
//        Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ClothesGeneralListingActivity.class);
        intent.putExtra("lowerPrice", lowerPrice.getText().toString());
//        if (lowerPrice.getText().toString().equals("")){
//            intent.putExtra("lowerPrice", 0);
//        } else {
//            intent.putExtra("lowerPrice", Integer.valueOf(lowerPrice.getText().toString()));
//        }
        intent.putExtra("higherPrice", higherPrice.getText().toString());
//        if (higherPrice.getText().toString().equals("")) {
//            intent.putExtra("higherPrice", 1000);
//        } else {
//            intent.putExtra("higherPrice", Integer.valueOf(higherPrice.getText().toString()));
//        }
        intent.putExtra("color", colourDropdown.getSelectedItem().toString());
        intent.putExtra("size", sizeDropdown.getSelectedItem().toString());
        intent.putExtra("type", typeName);

        String sortKey = "sortingType";
        if (ascendingCheckbox.isChecked()) {
            intent.putExtra(sortKey, SortingType.ASCENDING.name());
        } else if (descendingCheckbox.isChecked()) {
            intent.putExtra(sortKey, SortingType.DESCENDING.name());
        } else {
            intent.putExtra(sortKey, SortingType.NONE.name());
        }

        startActivity(intent);
    }
}



