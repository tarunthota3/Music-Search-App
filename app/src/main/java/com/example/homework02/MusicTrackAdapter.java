package com.example.homework02;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MusicTrackAdapter extends RecyclerView.Adapter<MusicTrackAdapter.ViewHolder> {
    ArrayList<MusicTrack> data;

    public MusicTrackAdapter(ArrayList<MusicTrack> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MusicTrackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.music_track_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicTrackAdapter.ViewHolder holder, int position) {
        MusicTrack musicTrack =data.get(position);
        holder.trackId.setText(musicTrack.name);
        holder.artistId.setText(musicTrack.artist);
        holder.priceId.setText(String.valueOf(musicTrack.trackPrice) + " $");

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(musicTrack.releaseDate, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        holder.dateId.setText(formattedDate);
        holder.musicTrack = musicTrack;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<MusicTrack> getData(){
        return data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "demo";
        static String MUSIC_TRACK_KEY = "MUSICTRACK";
        TextView trackId;
        TextView artistId;
        TextView priceId;
        TextView dateId;
        MusicTrack musicTrack;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackId = itemView.findViewById(R.id.trackId);
            artistId = itemView.findViewById(R.id.artistId);
            priceId = itemView.findViewById(R.id.priceId);
            dateId = itemView.findViewById(R.id.dateId);
            this.musicTrack = musicTrack;


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked item: " + musicTrack.toString());
                Intent intent = new Intent(v.getContext(),DisplayActivity.class);
                intent.putExtra(MUSIC_TRACK_KEY, musicTrack);
                v.getContext().startActivity(intent);
            }
        });
        }
    }
}
