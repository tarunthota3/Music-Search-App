package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("iTunes Music Search");

        setContentView(R.layout.activity_display);
        TextView textViewTrack = findViewById(R.id.textViewTrack);
        TextView textViewGenre = findViewById(R.id.textViewGenre);
        TextView textViewArtist = findViewById(R.id.textViewArtist);
        TextView textViewAlbum = findViewById(R.id.textViewAlbum);
        TextView textViewTrackPrice = findViewById(R.id.textViewTrackPrice);
        TextView textViewAlbumPrice = findViewById(R.id.textViewAlbumPrice);
        ImageView imageView = findViewById(R.id.imageView);
        Button button_finish = findViewById(R.id.buttonFinish);

        if(getIntent() != null && getIntent().getExtras() != null ){
            MusicTrack musicTrack = (MusicTrack) getIntent().getExtras().getSerializable(MusicTrackAdapter.ViewHolder.MUSIC_TRACK_KEY);
            textViewTrack.setText(musicTrack.name);
            textViewGenre.setText(musicTrack.genre);
            textViewArtist.setText(musicTrack.artist);
            textViewAlbum.setText(musicTrack.album);
            textViewTrackPrice.setText(musicTrack.trackPrice + " $");
            textViewAlbumPrice.setText(musicTrack.albumPrice + " $");
            Picasso.get().load(musicTrack.artworkUrl100).into(imageView);

        }

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
