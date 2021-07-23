package com.inmovies.inmovies.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public LiveData<List<MovieModel>> getMovies(){
        return apiClient.getMovies();
    }

    // Calling the searchMovieApi
    public void searchMovies(int pageNumber){
        apiClient.searchPopularMoviesApi(pageNumber);
    }

}
