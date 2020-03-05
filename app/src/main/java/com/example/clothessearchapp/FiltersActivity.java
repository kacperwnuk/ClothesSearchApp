package com.example.clothessearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<String> colours = new ArrayList<>(Arrays.asList("", "Niebieski", "Zielony", "Pomarańczowy", "Czarny", "Różowy", "Czerwony"));
    private List<String> sizes = new ArrayList<>(Arrays.asList("", "S", "M", "L", "XL"));

    private boolean colourChecked = false;
    private boolean sizeChecked = false;

    private Spinner colourDropdown;
    private Spinner sizeDropdown;
    private TextView lowerPrice;
    private TextView higherPrice;
    private CheckBox ascendingCheckbox;
    private CheckBox descendingCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

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
    }

    private void initializeDropdowns() {
        colourDropdown = findViewById(R.id.color_spinner);
        colourDropdown.setOnItemSelectedListener(this);
        sizeDropdown = findViewById(R.id.size_spinner);
        sizeDropdown.setOnItemSelectedListener(this);

        ArrayAdapter<String> colourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colours);
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);

        colourDropdown.setAdapter(colourAdapter);
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
        String text = lowerPrice.getText() + " " + higherPrice.getText() + " " + colourDropdown.getSelectedItem().toString() + " " + sizeDropdown.getSelectedItem().toString();
//        Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ResultsActivity.class);
        if (lowerPrice.getText().toString().equals("")){
            intent.putExtra("lowerPrice", 0);
        } else {
            intent.putExtra("lowerPrice", Integer.valueOf(lowerPrice.getText().toString()));
        }

        if (higherPrice.getText().toString().equals("")) {
            intent.putExtra("higherPrice", 1000);
        } else {
            intent.putExtra("higherPrice", Integer.valueOf(higherPrice.getText().toString()));
        }
        intent.putExtra("color", colourDropdown.getSelectedItem().toString());
        intent.putExtra("size", sizeDropdown.getSelectedItem().toString());

        String sortKey = "sortingType";
        if (ascendingCheckbox.isChecked()){

            intent.putExtra(sortKey, SortingType.ASCENDING.name());
        } else if (descendingCheckbox.isChecked()){
            intent.putExtra(sortKey, SortingType.DESCENDING.name());
        } else {
            intent.putExtra(sortKey, SortingType.NONE.name());
        }

        startActivity(intent);
    }
}



