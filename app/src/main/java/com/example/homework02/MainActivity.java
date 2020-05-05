/*
Assignment: In Class Assignment 04 Homework
File name: MainActivity.java
Full name:
Akhil Madhamshetty-801165622
Tarun thota-801164383
 */
package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "demo";
    TextView editTextKeyword;
    SeekBar seekBar;
    TextView textViewLimit;
    Button buttonSearch;
    Button buttonReset;
    Switch switchButton;
    ProgressBar progressBar;
    int limitValue = 10;
//    String sortByValue = "Price";
    String baseURL = " https://itunes.apple.com/search";
    ArrayList<MusicTrack> tracks = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Music Search");

        editTextKeyword = findViewById(R.id.editTextKeyword);
        seekBar = findViewById(R.id.seekBar);
        textViewLimit = findViewById(R.id.textViewLimit);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonReset = findViewById(R.id.buttonReset);
        switchButton = findViewById(R.id.switchButton);
        progressBar = findViewById(R.id.progressBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limitValue = progress;
                textViewLimit.setText("Limit " + limitValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tracks.size() != 0){
                 tracks = new ArrayList<>();
                 switchButton.setChecked(true);
                }

                if(editTextKeyword.getText().toString().length() == 0){
                    editTextKeyword.setError("Keyword can't be empty");
                    editTextKeyword.setText("");
                    seekBar.setProgress(10);
                    textViewLimit.setText("Limit 10");
                    recyclerView.removeAllViewsInLayout();
                }
                else{
                    new getJSONData().execute(editTextKeyword.getText().toString(),String.valueOf(limitValue));
                    Log.d(TAG, "onClick: " + limitValue + editTextKeyword.getText().toString());
                }

            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button reset clicked: " + mAdapter.getItemCount());
                editTextKeyword.setText("");
                seekBar.setProgress(10);
                textViewLimit.setText("Limit 10");
                switchButton.setChecked(true);

                ArrayList<MusicTrack> arrayList = new ArrayList<>();
                mAdapter = new MusicTrackAdapter(arrayList);
                recyclerView.setAdapter(mAdapter);
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    sortByValue = "Date";
                    Collections.sort(tracks,MusicTrack.dateComparator);


                    mAdapter =  new MusicTrackAdapter(tracks);
                    recyclerView.setAdapter(mAdapter);
                }
                else{
//                    sortByValue = "Price";
                    Collections.sort(tracks,MusicTrack.priceComparator);
                    mAdapter =  new MusicTrackAdapter(tracks);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    class getJSONData extends AsyncTask<String, Void, ArrayList<MusicTrack>>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            tracks = new ArrayList<>();
        }

        @Override
        protected ArrayList<MusicTrack> doInBackground(String... strings) {
            String keyword = strings[0];
            String limitvalue = strings[1];
            Log.d(TAG, "Keyword: " + keyword + " & Limit: " + limitvalue);

            HttpURLConnection connection = null;


            URL url1 = null;
            try {
                String url = baseURL + "?"
                        + "term=" + URLEncoder.encode(keyword,"UTF-8")
                        + "&" + "limit=" + URLEncoder.encode(limitvalue,"UTF-8");
                url1 = new URL(url);
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray results = root.getJSONArray("results");

                    for(int i = 0; i < results.length(); i++){

                        JSONObject resultJSON = results.getJSONObject(i);

                        MusicTrack musicTrack = new MusicTrack();
                        String trackName = resultJSON.getString("trackName");
                        String genre = resultJSON.getString("primaryGenreName");
                        String artistName = resultJSON.getString("artistName");
                        String album = resultJSON.getString("collectionName");
                        Double trackPrice = resultJSON.getDouble("trackPrice");
                        Double albumPrice = resultJSON.getDouble("collectionPrice");
                        String releaseDate = resultJSON.getString("releaseDate");
                        String artworkUrl100 = resultJSON.getString("artworkUrl100");
                        if( trackName == "" || trackName == null ){
                            musicTrack.name = "Track Name not available";
                        }
                        else{
                            musicTrack.name = trackName;
                        }
                        if( genre == "" || genre == null ){
                            musicTrack.genre = "Genre not available";
                        }
                        else{
                            musicTrack.genre = genre;
                        }
                        if( artistName == "" || artistName == null ){
                            musicTrack.artist = "Artist Name not available";
                        }
                        else{
                            musicTrack.artist = artistName;
                        }
                        if( album == "" || album == null ){
                            musicTrack.album = "Album name not available";
                        }
                        else{
                            musicTrack.album = album;
                        }
                        if( trackPrice == null ){
                            musicTrack.trackPrice = 0;
                        }
                        else{
                            musicTrack.trackPrice = trackPrice;
                        }
                        if( albumPrice == null ){
                            musicTrack.albumPrice = 0;
                        }
                        else{
                            musicTrack.albumPrice = albumPrice;
                        }
                        if(releaseDate == "" || releaseDate == null){
                            musicTrack.releaseDate = "Release Date Not available";
                        }
                        else{
                            musicTrack.releaseDate = releaseDate;
                        }
                        if(artworkUrl100 == "" || artworkUrl100 == null){
                            musicTrack.artworkUrl100 = "artworkUrl100 Not available";
                        }
                        else{
                            musicTrack.artworkUrl100 = artworkUrl100;
                        }

                        tracks.add(musicTrack);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return tracks;
        }

            @Override
        protected void onPostExecute(ArrayList<MusicTrack> s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
                for (MusicTrack musicTrack: s) {
                    Log.d(TAG, "onPostExecute: " + musicTrack.toString());
                }

                recyclerView = findViewById(R.id.recycler_view);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                switchButton.setChecked(true);
                Collections.sort(s,MusicTrack.dateComparator);
                mAdapter = new MusicTrackAdapter(s);
                recyclerView.setAdapter(mAdapter);

        }
    }
}
