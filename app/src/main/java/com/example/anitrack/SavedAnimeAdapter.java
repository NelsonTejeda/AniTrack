package com.example.anitrack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class SavedAnimeAdapter extends RecyclerView.Adapter<SavedAnimeAdapter.ViewHolder>{
    private static final String TAG = "SavedAnimeAdapter";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map> listOfAnime;

    public SavedAnimeAdapter(Context context, ArrayList<Map> listOfAnime) {
        this.context = context;
        this.listOfAnime = listOfAnime;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_anime_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(context).load()
        currentUser = mAuth.getCurrentUser();
        holder.animeName.setText(listOfAnime.get(position).get("name").toString());
        Glide.with(context).load(listOfAnime.get(position).get("imgURL")).override(400,600).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listOfAnime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView animeName;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivSavedAnimeImage);
            animeName = itemView.findViewById(R.id.tvSavedAnimeName);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutSavedAnime);
        }
    }


}
