package com.inmovies.inmovies.utils;

import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.response.MoviePopularResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // interface used by Retrofit

    // Get the popular movie details
    // https://api.themoviedb.org/3/movie/popular?api_key=07954711307c2f5ace3eee417e423cef&page=1
    @GET("/3/movie/popular")
    Call<MoviePopularResponse> popularMovies(
            @Query("api_key") String api_key,
            @Query("page") String page
    );

    // Get the now playing movie details
    // https://api.themoviedb.org/3/movie/now_playing?api_key=07954711307c2f5ace3eee417e423cef&page=1
    @GET("/3/movie/now_playing")
    Call<MovieNowPlayingResponse> nowPlayingMovie(
            @Query("api_key") String api_key,
            @Query("page") String page
    );

}