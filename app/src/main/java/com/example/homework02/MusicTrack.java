package com.example.homework02;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MusicTrack implements Serializable {
    String name, genre, artist, album;
    double trackPrice, albumPrice;
    String releaseDate;
    String artworkUrl100;

    public MusicTrack() {
    }

    public static Comparator<MusicTrack> priceComparator = new Comparator<MusicTrack>() {

        @Override
        public int compare(MusicTrack o1, MusicTrack o2) {
            double trackPrice1 = o1.trackPrice;
            double trackPrice2 = o2.trackPrice;

            if(trackPrice1 < trackPrice2){
                return -1;
            }
            else{
                return 1;
            }
        }
    };
    public static Comparator<MusicTrack> dateComparator = new Comparator<MusicTrack>() {

        @Override
        public int compare(MusicTrack o1, MusicTrack o2) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date1 = null;
            Date date2 = null;

            try {
                date1 = formatter.parse(o1.releaseDate);
                date2 = formatter.parse(o2.releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.before(date2)){
                return -1;
            } else if (date1.after(date2)) {
                return 1;
            } else {
                return 0;
            }
        }
    };


    @Override
    public String toString() {
        return "MusicTrack{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", trackPrice=" + trackPrice +
                ", albumPrice=" + albumPrice +
                ", releaseDate='" + releaseDate + '\'' +
                ", artworkUrl100='" + artworkUrl100 + '\'' +
                '}';
    }
}
