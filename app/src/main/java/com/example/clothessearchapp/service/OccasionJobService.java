package com.example.clothessearchapp.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.activities.ClothesGeneralListingActivity;
import com.example.clothessearchapp.adapters.ClothesRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.notification.NotificationID;
import com.example.clothessearchapp.notification.NotificationUtils;
import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.Occasion;
import com.example.clothessearchapp.structure.Type;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OccasionJobService extends JobService {

    private static int counter = 0;
    private String token;

    @Override
    public boolean onStartJob(JobParameters params) {
        token = loadToken();

        Gson gson = new Gson();
        Occasion occasion = gson.fromJson(params.getExtras().getString("occasion"), Occasion.class);

        searchClothes(occasion);
        return true;
    }

    private void searchClothes(Occasion occasion) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);
        Call<List<Clothes>> call = service.getClothes("Token " + token, Type.englishNames.get(occasion.getType()), occasion.getColor(), occasion.getSize(), "", occasion.getPrice(), "ascending");
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {

                if(response.body() != null && response.body().size() != 0){
                    sendNotification(occasion);
                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }


    @Override
    public boolean onStopJob(JobParameters params) {
        System.out.println("Kończenie pracy!");
        return false;
    }


    private void sendNotification(Occasion occasion) {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        String title = getString(R.string.occasion_found);
        String body = String.format("Kryteria: %s %s %s %s", occasion.getType(), occasion.getSize(), occasion.getColor(), occasion.getPrice());
        NotificationCompat.Builder builder = notificationUtils.getAndroidChannelNotification(title, body);
        notificationUtils.getManager().notify(NotificationID.getID(), builder.build());
    }

    private String loadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", 0);
        return sharedPreferences.getString(getString(R.string.token), "");
    }

}
