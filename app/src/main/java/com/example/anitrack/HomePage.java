package com.example.anitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomePage extends AppCompatActivity {
    private static final String TAG = "HomePage";
    String season = "spring";
    String year = "2021";
    List<Anime> animes;
    List<Anime> temp;
    HomeListAdapter homeListAdapter;
    private BottomNavigationView bottomNavigationView;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> imageUrl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // do something here
                        return true;
                    case R.id.action_profile:
                        // do something here
                        return true;
                    default: return true;
                }
            }
        });


        animes = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(this,animes);
        getDataFromApi();
    }

    private void getDataFromApi(){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("https://kitsu.io/api/edge/anime?filter%5Bseason%5D=" + season +"&filter%5BseasonYear%5D=" + year)
                .addPathParameter("page", "0")
                .addQueryParameter("limit", "20")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString());
                            JSONArray data = response.getJSONArray("data");
                            System.out.println(data.toString());
                            animes.addAll(Anime.fromJsonArray(data));
                            homeListAdapter.notifyDataSetChanged();
                            System.out.println(animes.size());
                            initRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        System.out.println(error.getErrorDetail());
                        System.out.println(error.toString());
                    }
                });
    }

    private void initRecyclerView(){
        Log.d(TAG, "init Happened");
        RecyclerView recyclerView = findViewById(R.id.rvHome);
        HomeListAdapter adapter = new HomeListAdapter(this,animes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}