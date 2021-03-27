package com.example.anitrack;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.google.gson.JsonArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    TextView animeTitle;
    String season = "spring";
    String year = "2021";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animeTitle = findViewById(R.id.animeTitle);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("https://kitsu.io/api/edge/anime?filter%5Bseason%5D=" + season +"&filter%5BseasonYear%5D=" + year)
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
                            JSONArray data = response.getJSONArray("data");
                            JSONObject firstSet = data.getJSONObject(0);
                            JSONObject attributeObj = firstSet.getJSONObject("attributes");
                            Log.d("DEBUG", attributeObj.toString());
                            ExecutorService threadpool = Executors.newCachedThreadPool();
                            Future<String> name = threadpool.submit(() -> attributeObj.get("slug").toString());
                            while(!name.isDone()){
                                System.out.println("FutureTask is not finished yet...");
                            }
                            animeTitle.setText(name.get());
                            //String name = attributeObj.get("slug").toString();
                            //animeTitle.setText(name.get());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
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