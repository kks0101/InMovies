package com.inmovies.inmovies.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inmovies.inmovies.LoadingDialog;
import com.inmovies.inmovies.MobileNavigationDirections;
import com.inmovies.inmovies.adapters.RecyclerViewAdapter;
import com.inmovies.inmovies.databinding.FragmentHomeBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView nowPlayingRecyclerView, popularMovieRecyclerView;
    private RecyclerViewAdapter nowPlayingRecyclerViewAdapter, popularMovieRecyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModelFactory homeViewModelFactory = new HomeViewModelFactory(getActivity().getApplication());
        homeViewModel =
                new ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nowPlayingRecyclerView = binding.nowPlayingRecyclerview;
        popularMovieRecyclerView = binding.popularPlayingRecyclerview;

        nowPlayingRecyclerViewAdapter = new RecyclerViewAdapter();
        popularMovieRecyclerViewAdapter = new RecyclerViewAdapter();

        popularMovieRecyclerView.setAdapter(popularMovieRecyclerViewAdapter);
        nowPlayingRecyclerView.setAdapter(nowPlayingRecyclerViewAdapter);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            popularMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), Constants.NUMBER_OF_COLUMNS_LANDSCAPE));
        }
        else{
            popularMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), Constants.NUMBER_OF_COLUMNS_PORTRAIT));
        }

        nowPlayingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //popularMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));


        observeChanges();
        searchMovies(1);
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
                    nowPlayingRecyclerViewAdapter.setMovieList(movieModels);
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
                    popularMovieRecyclerViewAdapter.setMovieList(movieModels);
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