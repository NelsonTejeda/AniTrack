package com.example.anitrack;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    //sizeOfImage can be tiny, small, medium, large, original
    private String sizeOfImage = "large";
    String animeCover;
    String animeTitle;
    String startDate;
    String endDate;
    int epiCount;
    String ageGuide;
    String description;
    String id;
    String youtubeVideoId;

    public Anime(){};

    public Anime(JSONObject jsonObject) throws JSONException{
        animeCover = jsonObject.getString("image_url");
        animeTitle = jsonObject.getString("title");
        startDate = "Start Date: " + jsonObject.getString("airing_start");
        description = jsonObject.getString("synopsis");
        youtubeVideoId = "null";
        if(youtubeVideoId == "null" || youtubeVideoId == null){
            //TODO: make a youtube video that states the video they are looking for doesn't exsit.
            youtubeVideoId = "-----";
        }
        if(jsonObject.getBoolean("r18") == false && jsonObject.getBoolean("kids") == false){
            ageGuide = "Age Guide: Teens";
        }
        else if(jsonObject.getBoolean("r18") == true){
            ageGuide = "Age Guide: 18+ / NSFW";
        }
        else {
            ageGuide = "Age Guide: Kids";
        }
        try{
            epiCount = jsonObject.getInt("episodes");
        }
        catch(Exception e){
            epiCount = 0;
        }
    }

    public static List<Anime> fromJsonArray(JSONArray animeJsonArray) throws JSONException{
        List<Anime> animes = new ArrayList<>();
        for(int i = 0; i < animeJsonArray.length(); i++){
            animes.add(new Anime(animeJsonArray.getJSONObject(i)));
            //Log.d("ANIME", animes.get(i).getAnimeTitle());
            //Log.d("ANIME", animes.get(i).getAnimeCover());
        }
        return animes;
    }

    public String getAnimeCover() {
        return animeCover;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getEpiCount() {
        return epiCount;
    }

    public String getAgeGuide() {
        return ageGuide;
    }

    public String getDescription() {
        return description;
    }

    public String getepiCountToString(){
        String v;
        v = String.valueOf(getEpiCount());
        if(v.equals("0")){
            v = "TBA";
        }
        v = "#Episodes: " + v;
        return v;
    }

    public String getId() {
        return id;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }
}
