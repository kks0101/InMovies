package com.inmovies.inmovies.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.inmovies.inmovies.models.MovieModel;

/**
 * Entity class for Bookmark Database
 */
@Entity(tableName = "bookmark_table")
public class BookmarkEntity {

    @PrimaryKey
    @NonNull
    public int id;

    public String title;

    public String poster_path;

    public String overview;

    public String backdrop_path;

    public float vote_average;

    public int vote_count;

    public String release_date;

    public BookmarkEntity(MovieModel movieModel){
        this.id = movieModel.getId();
        this.title = movieModel.getTitle();
        this.poster_path = movieModel.getPoster_path();
        this.overview = movieModel.getOverview();
        this.backdrop_path = movieModel.getBackdrop_path();
        this.vote_average = movieModel.getVote_average();
        this.vote_count = movieModel.getVote_count();
        this.release_date = movieModel.getRelease_date();
    }

    public BookmarkEntity(int id, String title, String poster_path, String overview, String backdrop_path, float vote_average, int vote_count, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
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

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getRelease_date() {
        return release_date;
    }
}
