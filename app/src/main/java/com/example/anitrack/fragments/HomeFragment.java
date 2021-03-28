package com.example.anitrack.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.anitrack.Anime;
import com.example.anitrack.HomeListAdapter;
import com.example.anitrack.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    String season = "spring";
    String year = "2021";
    List<Anime> animes;
    List<Anime> temp;
    HomeListAdapter homeListAdapter;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> imageUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animes = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(getContext(),animes);
        getDataFromApi(view);
    }

    private void getDataFromApi(View v){
        AndroidNetworking.initialize(getContext().getApplicationContext());
        AndroidNetworking.get("https://kitsu.io/api/edge/anime?filter%5Bseason%5D=" + season +"&filter%5BseasonYear%5D=" + year + "&page%5Blimit%5D=20")
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
                            initRecyclerView(v);
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

    private void initRecyclerView(View view){
        Log.d(TAG, "init Happened");
        RecyclerView recyclerView = view.findViewById(R.id.rvHome);
        HomeListAdapter adapter = new HomeListAdapter(getContext(),animes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}