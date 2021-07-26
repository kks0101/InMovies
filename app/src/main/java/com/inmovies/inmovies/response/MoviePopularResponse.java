package com.inmovies.inmovies.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

/**
 * GSON JSON Adapter to parse and serialize response obtained by TMDB Popular API
 *  https://api.themoviedb.org/3/movie/now_playing
 */


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

    /**
     *  get the list of MovieModel after serializing the "results" json array
     * @return List<MovieModel>
     */
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
