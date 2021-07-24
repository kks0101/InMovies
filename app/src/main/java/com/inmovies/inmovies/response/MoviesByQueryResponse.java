package com.inmovies.inmovies.response;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoviesByQueryResponse {

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movieLists;

    public List<MovieModel> getMoviesByQuery(){
        return movieLists;
    }


    @Override
    public String toString() {
        return "Query Movie Response : " + movieLists;
    }
}
