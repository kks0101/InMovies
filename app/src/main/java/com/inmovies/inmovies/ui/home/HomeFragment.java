package com.inmovies.inmovies.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
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
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nowPlayingRecyclerView = binding.nowPlayingRecyclerview;
        popularMovieRecyclerView = binding.popularPlayingRecyclerview;

        nowPlayingRecyclerViewAdapter = new RecyclerViewAdapter();
        popularMovieRecyclerViewAdapter = new RecyclerViewAdapter();

        popularMovieRecyclerView.setAdapter(popularMovieRecyclerViewAdapter);
        nowPlayingRecyclerView.setAdapter(nowPlayingRecyclerViewAdapter);

        nowPlayingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));


        observeChanges();
        searchMovies(1);

//        homeViewModel.searchMovieDetails(739542);
//        // testing movie details api
//        homeViewModel.getMovieDetailsById().observe(getViewLifecycleOwner(), new Observer<MovieModel>() {
//            @Override
//            public void onChanged(MovieModel movieModel) {
//                if(movieModel!=null){
//                    Log.v("details","home fragment "  +  movieModel.toString());
//                }
//                else{
//                    Log.v("details", "Unabel to get Details");
//                }
//            }
//        });
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