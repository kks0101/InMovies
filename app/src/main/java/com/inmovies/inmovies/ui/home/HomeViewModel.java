package com.inmovies.inmovies.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public HomeViewModel(Application application) {
        movieRepository = MovieRepository.getInstance(application);
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopularMovies();
    }

    public LiveData<List<MovieModel>> getNowPlayingMovies(){
        return movieRepository.getNowPlayingMovies();
    }

    // Home fragment show the list of now playing and popular movies:
    // Requesting the same from repository
    public void searchMovies(int pageNumber){
        movieRepository.searchMovies(pageNumber);
    }

    public LiveData<MovieModel> getMovieDetailsById(){
        return movieRepository.getMovieDetails();
    }
    public void searchMovieDetails(int movie_id){
        movieRepository.searchMovieDetails(movie_id);
    }
}