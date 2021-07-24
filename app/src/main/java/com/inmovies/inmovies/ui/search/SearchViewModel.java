package com.inmovies.inmovies.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.repositories.MovieRepository;

import java.util.List;


public class SearchViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public SearchViewModel(){
        movieRepository = MovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getMoviesByQuery() {
        return movieRepository.getMoviesByQuery();
    }
    public void searchMovies(String query, int pageNumber){
        movieRepository.searchMovies(query, pageNumber);
    }
}