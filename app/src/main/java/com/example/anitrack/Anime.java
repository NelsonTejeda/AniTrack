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

    public Anime(){};

    public Anime(JSONObject jsonObject) throws JSONException{
        animeCover = jsonObject.getJSONObject("attributes").getJSONObject("posterImage").getString(sizeOfImage);
        animeTitle = jsonObject.getJSONObject("attributes").getString("slug");
        startDate = "Start Date: " + jsonObject.getJSONObject("attributes").getString("startDate");
        if(jsonObject.getJSONObject("attributes").getString("endDate") == "null"){
            endDate = "End Date: Unknown";
        }
        else{
            endDate = "End Date: " + jsonObject.getJSONObject("attributes").getString("endDate");
        }
        try{
            epiCount = jsonObject.getJSONObject("attributes").getInt("episodeCount");
        }catch (Exception e){
            epiCount = 0;
        }
        if(jsonObject.getJSONObject("attributes").getString("ageRatingGuide") == "null"){
            ageGuide = "Age range not listed";
        }
        else{
            ageGuide = jsonObject.getJSONObject("attributes").getString("ageRatingGuide");
        }
        description = jsonObject.getJSONObject("attributes").getString("description");
        if(description == "null"){
            description = "No description listed...";
        }
        if(description.length() > 300){
            description = description.substring(0,300) + "...";
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
        v = "#Episodes: " + v;
        return v;
    }
}
