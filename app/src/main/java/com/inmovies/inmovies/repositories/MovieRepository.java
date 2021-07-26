package com.inmovies.inmovies.repositories;

import androidx.lifecycle.LiveData;
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

    private MovieRepository(){
        apiClient = ApiClient.getInstance();
    }

    public static MovieRepository getInstance(){
        if(instance==null)
            instance = new MovieRepository();
        return instance;
    }

    public LiveData<List<MovieModel>> getNowPlayingMovies(){
        return apiClient.getNowPlayingMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return apiClient.getPopularMovies();
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
