package com.inmovies.inmovies.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieNowPlayingResponse {

    // get the movie results - stored in **results** array in JSON response
    @SerializedName("results")
    @Expose()
    private List<MovieModel> nowPlayingMovies;

    public List<MovieModel> getNowPlayingMovies(){
        return nowPlayingMovies;
    }

    @Override
    public String toString() {
        return "Now Playing Movie response " + nowPlayingMovies;
    }
}
