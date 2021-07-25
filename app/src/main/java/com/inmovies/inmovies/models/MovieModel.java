package com.inmovies.inmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// specifies the data to be encapsulated for movie details fetched from API
// implementing parcelable to share data from one UI component(Fragment/Activity) to other
// e.g., get movie Detail for a given movie id
@Entity(tableName = "movie_table")
public class MovieModel implements Parcelable {

    private String title;
    private String poster_path;
    private String overview;
    private String backdrop_path;

    @PrimaryKey
    @NonNull
    private int id;
    private float vote_average;
    private int vote_count;
    private String release_date;

    
    private Long timestamp;


    public MovieModel(String title, String poster_path, String overview, String backdrop_path, int id, float vote_average, int vote_count, String release_date) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.release_date = release_date;

        timestamp = System.currentTimeMillis();
    }


    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        vote_count = in.readInt();
        release_date = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(backdrop_path);
        parcel.writeInt(id);
        parcel.writeFloat(vote_average);
        parcel.writeInt(vote_count);
        parcel.writeString(release_date);
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getId() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getRelease_date() {
        return release_date;
    }

    public static Creator<MovieModel> getCREATOR() {
        return CREATOR;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
