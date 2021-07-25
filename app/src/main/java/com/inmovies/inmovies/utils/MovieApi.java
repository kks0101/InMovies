package com.inmovies.inmovies.utils;

import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.response.MoviePopularResponse;
import com.inmovies.inmovies.response.MoviesByQueryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // interface used by Retrofit

    // Get the popular movie details
    // https://api.themoviedb.org/3/movie/popular?api_key=07954711307c2f5ace3eee417e423cef&page=1
    @GET("/3/movie/popular")
    Observable<MoviePopularResponse> popularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    // Get the now playing movie details
    // https://api.themoviedb.org/3/movie/now_playing?api_key=07954711307c2f5ace3eee417e423cef&page=1
    @GET("/3/movie/now_playing")
    Observable<MovieNowPlayingResponse> nowPlayingMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    // Get the movies by query
    // https://api.themoviedb.org/3/search/movie?api_key=07954711307c2f5ace3eee417e423cef&query=Jack&page=1
    @GET("3/search/movie")
    Observable<MoviesByQueryResponse> queryMovies(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

}
