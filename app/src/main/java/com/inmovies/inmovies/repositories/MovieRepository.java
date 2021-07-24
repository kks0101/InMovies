package com.inmovies.inmovies.repositories;

import androidx.lifecycle.LiveData;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.request.ApiClient;

import java.util.List;


// We are using MVVM architecture : This Singleton class acts as Repository to which ViewModel
// interacts, to get the data from the data source
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

    // call both the api: popular Movie as well as Now Playing
    public void searchMovies(int pageNumber){
        apiClient.searchPopularMovies(pageNumber);
        apiClient.searchNowPlayingMovies(pageNumber);
    }

    public void searchMovies(String query, int pageNumber){
        apiClient.searchMoviesByQuery(query, pageNumber);
    }

}
