package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.SortingType;
import com.example.clothessearchapp.adapters.OccasionRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.service.OccasionJobService;
import com.example.clothessearchapp.structure.Color;

import com.example.clothessearchapp.structure.Occasion;
import com.example.clothessearchapp.structure.Size;
import com.example.clothessearchapp.structure.Type;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchOccasionsActivity extends AppCompatActivity {

    private List<String> types;
    private List<String> colors;
    private List<String> sizes;
    private List<Occasion> occasions;

    private Spinner typeDropdown;
    private Spinner colorDropdown;
    private Spinner sizeDropdown;
    private TextView price;
    private Button submit;
    private RecyclerView recyclerView;
    private OccasionRecyclerAdapter occasionAdapter;

    private ArrayAdapter<String> typesAdapter;
    private ArrayAdapter<String> colorsAdapter;
    private ArrayAdapter<String> sizesAdapter;

    private String token;
    private GetDataService service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_occassion);

        token = loadToken();
        service = loadService();

        initializeDropdowns();

        price = findViewById(R.id.clothes_price);
        submit = findViewById(R.id.submit_button);
        submit.setEnabled(false);


        recyclerView = findViewById(R.id.recycler_occasions) ;
        recyclerView.setAdapter(occasionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadClothesTypes();
        loadOccasions();
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    submit.setEnabled(true);
                } else {
                    submit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    submit.setEnabled(true);
                } else {
                    submit.setEnabled(false);
                }
            }
        });

    }



    private void initializeDropdowns() {
        types = new ArrayList<>();
        colors = new ArrayList<>();
        sizes = new ArrayList<>();
        occasions = new ArrayList<>();

        typeDropdown = findViewById(R.id.type_spinner);
        colorDropdown = findViewById(R.id.color_spinner);
        sizeDropdown = findViewById(R.id.size_spinner);

        typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        colorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        sizesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        occasionAdapter = new OccasionRecyclerAdapter(occasions);

        typeDropdown.setAdapter(typesAdapter);
        colorDropdown.setAdapter(colorsAdapter);
        sizeDropdown.setAdapter(sizesAdapter);

        typeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearColorsAndSizes();
                loadColors(types.get(position));
                loadSizes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadOccasions() {
        Call<List<Occasion>> call = service.getOccasions("Token " + token);
        call.enqueue(new Callback<List<Occasion>>() {
            @Override
            public void onResponse(Call<List<Occasion>> call, Response<List<Occasion>> response) {
                List<Occasion> serverOccasions = response.body();
                if (serverOccasions != null){
                    for(Occasion occasion : serverOccasions){
                        occasion.setType(Type.polishNames.get(occasion.getType()));
                    }

                    occasions.clear();
                    occasions.addAll(response.body());
                    occasionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Occasion>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void clearColorsAndSizes() {
        colors.clear();
        sizes.clear();
        colorsAdapter.notifyDataSetChanged();
        sizesAdapter.notifyDataSetChanged();
    }

    private GetDataService loadService() {
        return RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);

    }

    private String loadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", 0);
        return sharedPreferences.getString(getString(R.string.token), "");
    }

    private void loadSizes() {
        Call<List<Size>> call2 = service.getSizes("Token " + token);
        call2.enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                List<Size> sizeList = response.body();
                sizes.clear();
                sizes.addAll(sizeList.stream().map(Size::getName).collect(Collectors.toList()));
                sizesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void loadColors(String clothesType) {
        Call<List<Color>> call = service.getColors("Token " + token, Type.englishNames.get(clothesType));

        call.enqueue(new Callback<List<Color>>() {
            @Override
            public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                colors.clear();
                colors.addAll(response.body().stream().map(Color::getName).collect(Collectors.toList()));
                colorsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Color>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void loadClothesTypes() {

        Call<List<Type>> call = service.getTypes("Token " + token);
        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(Call<List<Type>> call, Response<List<Type>> response) {
                types.clear();
                types.addAll(response.body().stream().map(Type::getPolishName).collect(Collectors.toList()));
                typesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Type>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }



    public void addOccasion(View view){
        String type =  (String)typeDropdown.getSelectedItem();
        String color = (String)colorDropdown.getSelectedItem();
        String size = (String)sizeDropdown.getSelectedItem();
        String price = this.price.getText().toString();
        Occasion occasion = new Occasion(UUID.randomUUID().toString(), type, color, size, price);

        occasions.add(occasion);
        runService(occasion);
        occasionAdapter.notifyDataSetChanged();
        addOccasionOnServer(occasion);

        Snackbar.make(findViewById(R.id.constraint_layout), "Dodano nowe poszukiwanie!", Snackbar.LENGTH_LONG).show();
    }

    private void runService(Occasion occasion) {

        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, OccasionJobService.class);

        Gson gson = new Gson();
        String json = gson.toJson(occasion);
        PersistableBundle pb = new PersistableBundle();
        pb.putString("occasion", json);

        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiresBatteryNotLow(true)
                .setExtras(pb)
//                .setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis())
                .setMinimumLatency(100)
                .build();
        jobScheduler.schedule(jobInfo);
    }

    private void addOccasionOnServer(Occasion occasion) {
        Call<Occasion> call = service.addOccasion("Token " + token, occasion.getKey(), Type.englishNames.get(occasion.getType()), occasion.getColor(), occasion.getSize(), occasion.getPrice());
        call.enqueue(new Callback<Occasion>() {
            @Override
            public void onResponse(Call<Occasion> call, Response<Occasion> response) {

            }

            @Override
            public void onFailure(Call<Occasion> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    public void deleteOccasion(View view){
        String key = (String)view.getTag();
        Optional<Occasion> occasion = occasions.stream().filter(c -> c.getKey().equals(key)).findFirst();
        occasion.ifPresent(value -> {
            occasions.remove(value);
            occasionAdapter.notifyDataSetChanged();
            deleteOccasionOnServer(value);
        });

    }

    public void showOccasionClothes(View view){

        String key = (String) view.getTag();
        Optional<Occasion> occasion = occasions.stream().filter(c -> c.getKey().equals(key)).findFirst();
        occasion.ifPresent( o -> {
            Intent intent = new Intent(this, ClothesGeneralListingActivity.class);

            intent.putExtra("higherPrice", o.getPrice());

            intent.putExtra("color", o.getColor());
            intent.putExtra("size", o.getSize());
            intent.putExtra("type", Type.englishNames.get(o.getType()));

            intent.putExtra("sortingType", SortingType.ASCENDING.name());

            startActivity(intent);
        });

    }

    private void deleteOccasionOnServer(Occasion occasion) {
        Call<Occasion> call = service.deleteOccasion("Token " + token, occasion.getKey());
        call.enqueue(new Callback<Occasion>() {
            @Override
            public void onResponse(Call<Occasion> call, Response<Occasion> response) {

            }

            @Override
            public void onFailure(Call<Occasion> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
