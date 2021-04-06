package com.example.anitrack.api;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AnimeAPI {

    private String season;
    private String year;
    private static final String TAG = "AnimeAPI";
    private String APIKEY;

    public AnimeAPI(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDateTime now = LocalDateTime.now();
        this.year = dtf.format(now).substring(0,4);
        String month = dtf.format(now).substring(5);

        //System.out.println(dtf.format(now));
        //Log.d(TAG, dtf.format(now));
        //changed

        switch (Integer.parseInt(month)){
            case 1:
                season = "winter";
                break;
            case 2:
                season = "winter";
                break;
            case 3:
                season = "winter";
            case 4:
                season = "spring";
                break;
            case 5:
                season = "spring";
                break;
            case 6:
                season = "spring";
                break;
            case 7:
                season = "summer";
                break;
            case 8:
                season = "summer";
                break;
            case 9:
                season = "summer";
                break;
            case 10:
                season = "fall";
                break;
            case 11:
                season = "fall";
                break;
            default:
                season = "fall";
                break;
        }

        APIKEY = "https://api.jikan.moe/v3/season/" + year + "/" + season;


    }

    public String getSeason() {
        return season;
    }

    public String getYear() {
        return year;
    }

    public String getAPIKEY() {
        return APIKEY;
    }
}
