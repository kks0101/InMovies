package com.inmovies.inmovies.ui.search;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.repositories.MovieRepository;

import java.util.List;


public class SearchViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public SearchViewModel(Application application){
        movieRepository = MovieRepository.getInstance(application);

    }

    public LiveData<List<MovieModel>> getMoviesByQuery() {
        return movieRepository.getMoviesByQuery();
    }
    public void searchMovies(String query, int pageNumber){
        movieRepository.searchMovies(query, pageNumber);
    }
}