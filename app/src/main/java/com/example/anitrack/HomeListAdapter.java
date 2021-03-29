package com.example.anitrack;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends  RecyclerView.Adapter<HomeListAdapter.ViewHolder>{
    private static final String TAG = "HomeListAdapter";
    private List<Anime> animes = new ArrayList<>();
    private Context context;

    public HomeListAdapter(Context context, List<Anime> listOfAnimes) {
        this.animes = listOfAnimes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_listitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"called");
        Log.d(TAG,animes.get(position).getAnimeCover());
        System.out.println(animes.get(position).getAnimeCover());
        Glide.with(context).load(animes.get(position).getAnimeCover()).into(holder.animeCover);
        holder.animeTitle.setText(animes.get(position).getAnimeTitle());
        if(animes.get(position).getStartDate().length() < 22){
            holder.animeStartDate.setText("Start Date: Unknown");
        }
        else{
            holder.animeStartDate.setText(animes.get(position).getStartDate().substring(0,22));
        }
        holder.ageGuide.setText(animes.get(position).getAgeGuide());
        holder.epiCount.setText(animes.get(position).getepiCountToString());

//        holder.animeEndDate.setText(animes.get(position).getEndDate());
//        holder.epiCount.setText(animes.get(position).getepiCountToString());
//        holder.ageGuide.setText(animes.get(position).getAgeGuide());
        String d = animes.get(position).getDescription();
        if(d.length() > 300){
            d = d.substring(0, 300) + "...";
        }
        holder.description.setText(d);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,animes.get(position).getAnimeTitle(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,AnimeContent.class);
                i.putExtra("title", animes.get(position).getAnimeTitle());
                i.putExtra("youtubeId", animes.get(position).getYoutubeVideoId());
                i.putExtra("description", "Description: \n\n" + animes.get(position).getDescription());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView animeCover;
        TextView animeTitle;
        TextView animeStartDate;
        TextView animeEndDate;
        TextView epiCount;
        TextView ageGuide;
        TextView description;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animeCover = itemView.findViewById(R.id.ivAnimeCover);
            animeTitle = itemView.findViewById(R.id.tvTitle);
            animeStartDate = itemView.findViewById(R.id.tvStartDate);
            animeEndDate = itemView.findViewById(R.id.tvEndDate);
            epiCount = itemView.findViewById(R.id.tvEpiCount);
            ageGuide = itemView.findViewById(R.id.tvAgeGuide);
            description = itemView.findViewById(R.id.tvDescription);
            layout = itemView.findViewById(R.id.homeListItem);
        }
    }


}
