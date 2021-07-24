package com.inmovies.inmovies.ui.moviedetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inmovies.inmovies.R;
import com.inmovies.inmovies.databinding.FragmentMovieDetailsBinding;
import com.inmovies.inmovies.models.MovieModel;

public class MovieDetailsFragment extends Fragment {

    private MovieDetailsViewModel movieDetailsViewModel;
    private FragmentMovieDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MovieDetailsFragmentArgs args = MovieDetailsFragmentArgs.fromBundle(getArguments());
        MovieModel movie = args.getMovieDetail();

        final TextView textView = binding.movieDetails;
        textView.setText(movie.getTitle());
//        movieDetailsViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}