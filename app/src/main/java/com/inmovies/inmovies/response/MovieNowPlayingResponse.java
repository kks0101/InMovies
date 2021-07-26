package com.inmovies.inmovies.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

/**
 * GSON JSON Adapter to parse and serialize response obtained by TMDB Now Playing API
 * https://api.themoviedb.org/3/movie/now_playing
 */

public class MovieNowPlayingResponse {

    // get the movie results - stored in **results** array in JSON response
    @SerializedName("results")
    @Expose()
    private List<MovieModel> nowPlayingMovies;

    /**
     *  get the list of MovieModel after serializing the "results" json array
     * @return List<MovieModel>
     */
    public List<MovieModel> getNowPlayingMovies(){
        return nowPlayingMovies;
    }

    @Override
    public String toString() {
        return "Now Playing Movie response " + nowPlayingMovies;
    }
}
