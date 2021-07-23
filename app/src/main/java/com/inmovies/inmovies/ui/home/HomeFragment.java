package com.inmovies.inmovies.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.inmovies.inmovies.databinding.FragmentHomeBinding;
import com.inmovies.inmovies.models.MovieModel;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        observeChanges();


        final Button button = binding.buttonHome;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovies(1);
            }
        });

        return root;
    }


    // search for movies : both popular and now playing movies
    public void searchMovies(int pageNumber){
        homeViewModel.searchMovies(pageNumber);
    }

    public void observeChanges(){

        //observer for now playing movies
        homeViewModel.getNowPlayingMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for data change
                if(movieModels != null){
                    for(MovieModel movie: movieModels){
                        Log.v("tag", "Now Playing Title: " + movie.getTitle());
                    }
                }
            }
        });


        // observer for popular movies
        homeViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movie: movieModels){
                        Log.v("tag", "Popular Movie Title: " + movie.getTitle());
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}