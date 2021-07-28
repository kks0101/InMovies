package com.inmovies.inmovies.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.inmovies.inmovies.database.DbClient;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.request.ApiClient;

import java.util.List;

/**
 * // We are using MVVM architecture : This Singleton class acts as Repository to which ViewModel
 * // interacts, to get the data from the data source
 * Currently in this app, this repository interacts with Api call/service (remote data source)
 */
public class MovieRepository {

    // Singleton Class
    private static MovieRepository instance;

    private ApiClient apiClient;
    private DbClient dbClient;

    private MovieRepository(Context context) {
        dbClient = DbClient.getInstance(context);
        apiClient = ApiClient.getInstance(context);
    }

    public static MovieRepository getInstance(Context context){
        if(instance==null)
            instance = new MovieRepository(context);
        return instance;
    }

    public LiveData<List<MovieModel>> getNowPlayingMovies(){
        // single source of data is database: we always retrieve data from the database
        // and at the same time update the data from the api
        // this enables offline working of the app
        apiClient.searchNowPlayingMovies(1);


        return dbClient.getAllNowPlayingMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        apiClient.searchPopularMovies(1);
        return dbClient.getAllPopularMovies();
    }

    public LiveData<List<MovieModel>> getMoviesByQuery(){
        return apiClient.getMoviesByQuery();
    }

    /**
     * call both the api to get list of movies: popular Movie as well as Now Playing
     * @param pageNumber
     */
    public void searchMovies(int pageNumber){
        apiClient.searchPopularMovies(pageNumber);
        apiClient.searchNowPlayingMovies(pageNumber);
    }

    /**
     * call the api to get list of movies by query string
     * @param query
     * @param pageNumber
     */
    public void searchMovies(String query, int pageNumber){
        apiClient.searchMoviesByQuery(query, pageNumber);
    }


    public LiveData<MovieModel> getMovieDetails(){
        return apiClient.getMovieDetails();
    }

    /**
     * call the api to get the details of a particular movie given by movie id
     * @param movie_id
     */
    public void searchMovieDetails(int movie_id){
        apiClient.searchMovieDetails(movie_id);
    }
}
