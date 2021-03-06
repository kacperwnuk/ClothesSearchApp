package com.example.clothessearchapp.network;

import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.DetailedClothes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://localhost:8000";

    public static Retrofit getRetrofitInstance(boolean detailed) {
        GsonConverterFactory gsonConverterFactory;
        if (detailed){
            RuntimeTypeAdapterFactory runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                    .of(Clothes.class)
                    .registerSubtype(DetailedClothes.class);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                    .create();
            gsonConverterFactory = GsonConverterFactory.create(gson);
        } else {
            gsonConverterFactory =  GsonConverterFactory.create();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
