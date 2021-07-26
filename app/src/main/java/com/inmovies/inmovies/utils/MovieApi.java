package com.inmovies.inmovies.utils;

import com.inmovies.inmovies.response.MovieDetailsResponse;
import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.response.MoviePopularResponse;
import com.inmovies.inmovies.response.MoviesByQueryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface used by Retrofit: Retrofit turn HTTP API into this JAVA interface
 */
public interface MovieApi {


    /**
     * HTTP API endpoint for popular movies
     *  https://api.themoviedb.org/3/movie/popular?api_key=<api_key>&page=<page_number>
     * @param api_key
     * @param page
     * @return RxJava Observable object
     */
    @GET("/3/movie/popular")
    Observable<MoviePopularResponse> popularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    /**
     * HTTP API endpoint for now playing movies
     *  https://api.themoviedb.org/3/movie/now_playing?api_key=<api_key>&page=<page_number>
     * @param api_key
     * @param page
     * @return RxJava Observable object
     */
    @GET("/3/movie/now_playing")
    Observable<MovieNowPlayingResponse> nowPlayingMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    /**
     * HTTP API endpoint for searching movies based on query string
     *  https://api.themoviedb.org/3/search/movie?api_key=<api_key>&query=<query_string>&page=<page_number>
     * @param api_key
     * @param page
     * @return RxJava Observable object
     */
    @GET("3/search/movie")
    Observable<MoviesByQueryResponse> queryMovies(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    /**
     * HTTP API endpoint to get the movie details by movie id
     *  https://api.themoviedb.org/3/movie/page?api_key=<api_key>
     * @param api_key
     * @param id
     * @return RxJava Observable object
     */
    @GET("3/movie/{movie_id}")
    Observable<MovieDetailsResponse> getMovieDetails(

            @Path("movie_id") int id,
            @Query("api_key") String api_key

    );

}
