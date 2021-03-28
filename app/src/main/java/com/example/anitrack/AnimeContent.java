package com.example.anitrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class AnimeContent extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private TextView title;
    private TextView description;

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

        String ytId = getIntent().getStringExtra("youtubeId");
        youTubePlayerView = findViewById(R.id.ytp);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                //super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(ytId,0);
            }
        });


    }
}