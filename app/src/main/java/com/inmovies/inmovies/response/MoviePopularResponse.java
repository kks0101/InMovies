package com.inmovies.inmovies.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoviePopularResponse {

    // get total results
    @SerializedName("total_results")
    @Expose()
    private int totalResults;

    public int getTotalResults(){
        return totalResults;
    }

    @SerializedName("results")
    @Expose()
    private List<MovieModel> popularMovieList;

    public List<MovieModel> getPopularMovieList(){
        return popularMovieList;
    }

    @Override
    public String toString() {
        return "Popular Movies : {" +
                "total count: " + totalResults +
                "movies: " + popularMovieList;
    }
}
