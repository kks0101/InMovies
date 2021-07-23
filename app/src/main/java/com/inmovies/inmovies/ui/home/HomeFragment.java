package com.inmovies.inmovies.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.inmovies.inmovies.R;
import com.inmovies.inmovies.databinding.FragmentHomeBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.request.ServiceRequest;
import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.utils.Constants;
import com.inmovies.inmovies.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        final Button button = binding.buttonHome;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNowPlayingMovies();
            }
        });
        return root;
    }

    public void getNowPlayingMovies(){
        MovieApi movieApi = ServiceRequest.getMovieApi();

        Call<MovieNowPlayingResponse> nowPlayingResponseCall = movieApi.nowPlayingMovie(
                Constants.API_KEY,
                "1"
        );

        nowPlayingResponseCall.enqueue(new Callback<MovieNowPlayingResponse>() {
            @Override
            public void onResponse(Call<MovieNowPlayingResponse> call, Response<MovieNowPlayingResponse> response) {

                if(response.code() == 200){
                    Log.v("tag", "the response " + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getNowPlayingMovies());

                    for(MovieModel movie : movies){
                        Log.v("tag", "Title: " + movie.getTitle());
                    }
                }else{

                    Log.v("tag", response.errorBody().toString());

                }

            }

            @Override
            public void onFailure(Call<MovieNowPlayingResponse> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}