package com.example.clothessearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Type;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseTypeActivity extends AppCompatActivity {

    private List<Type> types;
    private TypesRecyclerAdapter adapter;
    private MyRecyclerAdapter recyclerAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        progressDialog = new ProgressDialog(ChooseTypeActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        showTypes();

    }


    private void showTypes(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);
        Call<List<Type>> call = service.getTypes();
        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(Call<List<Type>> call, Response<List<Type>> response) {
                progressDialog.dismiss();
                types = response.body();
//                List<String> stringTypes = types.stream().map(Type::getName).collect(Collectors.toList());
                adapter = new TypesRecyclerAdapter(types);
//                recyclerAdapter = new MyRecyclerAdapter(stringTypes);
                RecyclerView recycler = findViewById(R.id.recycler_view);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new GridLayoutManager(ChooseTypeActivity.this, 2));
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
        intent.putExtra("clothesType", ((TextView)view.findViewById(R.id.type_name)).getText());
        startActivity(intent);
    }

}
