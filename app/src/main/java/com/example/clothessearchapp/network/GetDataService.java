package com.example.clothessearchapp.network;

import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.Color;
import com.example.clothessearchapp.structure.DetailedClothes;
import com.example.clothessearchapp.structure.Occasion;
import com.example.clothessearchapp.structure.Size;
import com.example.clothessearchapp.structure.Type;
import com.example.clothessearchapp.structure.UserCredentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/types")
    Call<List<Type>> getTypes(@Header("Authorization") String auth);

    @GET("/sizes")
    Call<List<Size>> getSizes(@Header("Authorization") String auth);

    @GET("/colors/{clothType}")
    Call<List<Color>> getColors(@Header("Authorization") String auth, @Path("clothType") String clothType);

    @GET("/clothes")
    Call<List<Clothes>> getClothes(@Header("Authorization") String auth, @Query("type") String type, @Query("color") String color, @Query("size") String size, @Query("lowerPrice") String lowerPrice, @Query("higherPrice") String higherPrice, @Query("sortType") String sortType);

    @GET("/detailed")
    Call<DetailedClothes> getDetailedClothes(@Header("Authorization") String auth, @Query("id") String id, @Query(value="shop", encoded=true) String shop);

    @FormUrlEncoded
    @POST("/auth")
    Call<String> getToken(@Field("username")String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/register")
    Call<UserCredentials> register(@Field("username")String username, @Field("email")String email, @Field("password")String password);

    @GET("/favourites")
    Call<List<Clothes>> getFavourites(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("/favourites")
    Call<Clothes> addFavourite(@Header("Authorization") String auth, @Field("key") String key);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/favourites", hasBody = true)
    Call<Clothes> deleteFavourite(@Header("Authorization") String auth, @Field("key") String key);

    @GET("/occasions")
    Call<List<Occasion>>  getOccasions(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("/occasions")
    Call<Occasion> addOccasion(@Header("Authorization") String auth, @Field("key") String key, @Field("type") String type, @Field("color") String color, @Field("size") String size, @Field("price") String price);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/occasions", hasBody = true)
    Call<Occasion> deleteOccasion(@Header("Authorization") String auth, @Field("key") String key);

    @GET("/email")
    Call<String> sendEmail(@Header("Authorization") String auth);

}
