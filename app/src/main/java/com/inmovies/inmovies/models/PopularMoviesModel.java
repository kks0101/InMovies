package com.inmovies.inmovies.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "popular_movie_table")
public class PopularMoviesModel {

    @PrimaryKey
    @NonNull
    private int movie_id;

    public PopularMoviesModel(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
