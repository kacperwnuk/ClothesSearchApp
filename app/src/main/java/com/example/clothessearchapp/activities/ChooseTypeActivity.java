package com.example.clothessearchapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.adapters.MyRecyclerAdapter;
import com.example.clothessearchapp.adapters.TypesRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Type;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseTypeActivity extends AppCompatActivity {

    private List<Type> types;
    private TypesRecyclerAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        types =  new ArrayList<>();

        progressDialog = new ProgressDialog(ChooseTypeActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        showTypes();
        adapter = new TypesRecyclerAdapter(types);
        RecyclerView recycler = findViewById(R.id.recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(ChooseTypeActivity.this, 2));
    }


    private void showTypes(){

        SharedPreferences sharedPreferences = getSharedPreferences("Data", 0);
        String token = sharedPreferences.getString(getString(R.string.token), "");

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);
        Call<List<Type>> call = service.getTypes("Token " + token);
        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(Call<List<Type>> call, Response<List<Type>> response) {
                progressDialog.dismiss();
                types.clear();
                types.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Type>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });
    }

    public void goToFilter(View view){
        Intent intent = new Intent(this, FiltersActivity.class);
        intent.putExtra("clothesType", (String)(view.findViewById(R.id.type_name)).getTag());
        startActivity(intent);
    }

}
