package com.inmovies.inmovies.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;


/**
 * GSON JSON Adapter to parse and serialize response obtained by using TMDB search API
 * https://api.themoviedb.org/3/search/movie?api_key=<api_key>&query=Jack&page=<page_no>
 */
public class MoviesByQueryResponse {

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movieLists;

    /**
     *  get the list of MovieModel after serializing the "results" json array
     * @return List<MovieModel>
     */
    public List<MovieModel> getMoviesByQuery(){
        return movieLists;
    }


    @Override
    public String toString() {
        return "Query Movie Response : " + movieLists;
    }
}
