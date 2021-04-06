package com.example.anitrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.anitrack.api.YoutubeAPI;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnimeContent extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private TextView title;
    private TextView description;
    private String titleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_content);



        String d = getIntent().getStringExtra("description");
        description = findViewById(R.id.contentDescription);
        description.setText(d);

        String t = getIntent().getStringExtra("title");
        title = findViewById(R.id.contentTitle);
        title.setText(t);

        titleId = t.replaceAll("\\s","-");
        titleId = titleId + "-trailer-en-sub";

        AndroidNetworking.get("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=" + titleId + "&type=video&key=" + YoutubeAPI.YOUTUBE_API_KEY)
                .addPathParameter("page", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("items");
                            JSONObject idSet = data.getJSONObject(0);
                            JSONObject idNum = idSet.getJSONObject("id");

                            String ytId = idNum.getString("videoId");
                            youTubePlayerView = findViewById(R.id.ytp);
                            getLifecycle().addObserver(youTubePlayerView);

                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                    //super.onReady(youTubePlayer);
                                    youTubePlayer.loadVideo(ytId,0);
                                }
                            });

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
}