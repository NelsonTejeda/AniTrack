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
import android.widget.Button;

import com.example.anitrack.HomeListAdapter;
import com.example.anitrack.R;
import com.example.anitrack.SavedAnimeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SavedAnimeFragment extends Fragment {
    private static final String TAG = "SavedAnimeFragment";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button buttonCallDB;
    private ArrayList<Map> savedAnimes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_anime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        buttonCallDB = view.findViewById(R.id.getDBAnime);
        buttonCallDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnimes();
            }
        });
        savedAnimes = new ArrayList<>();
        db.collection("users/" + currentUser.getUid() + "/animes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                                savedAnimes.add(document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        initRecyclerView(view);
                    }
                });

    }

    private void getAnimes(){
        currentUser = mAuth.getCurrentUser();
        db.collection("users/" + currentUser.getUid() + "/animes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void initRecyclerView(View view){
        Log.d(TAG, "init Happened");
        RecyclerView recyclerView = view.findViewById(R.id.rvSavedAnime);
        SavedAnimeAdapter adapter = new SavedAnimeAdapter(getContext(),savedAnimes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}