package com.inmovies.inmovies.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * bookmark table to store movie id in separate table
 */
@Entity(tableName = "bookmark_table")
public class BookmarkModel {

    @PrimaryKey
    @NonNull
    private int movie_id;

    public BookmarkModel(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
