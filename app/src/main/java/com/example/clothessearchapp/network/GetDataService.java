package com.example.clothessearchapp.network;

import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.Color;
import com.example.clothessearchapp.structure.DetailedClothes;
import com.example.clothessearchapp.structure.Size;
import com.example.clothessearchapp.structure.Type;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/types")
    Call<List<Type>> getTypes();

    @GET("/sizes")
    Call<List<Size>> getSizes();

    @GET("/colors/{clothType}")
    Call<List<Color>> getColors(@Path("clothType") String clothType);

    @GET("/clothes")
    Call<List<Clothes>> getClothes(@Query("type") String type, @Query("color") String color, @Query("size") String size, @Query("lowerPrice") String lowerPrice, @Query("higherPrice") String higherPrice, @Query("sortType") String sortType);

    @GET("/detailed")
    Call<DetailedClothes> getDetailedClothes(@Query("id") String id, @Query(value="shop", encoded=true) String shop);
}
