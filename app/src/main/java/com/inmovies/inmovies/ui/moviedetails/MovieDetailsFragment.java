package com.inmovies.inmovies.ui.moviedetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.inmovies.inmovies.databinding.FragmentMovieDetailsBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.utils.Constants;

import org.w3c.dom.Text;

public class MovieDetailsFragment extends Fragment {

    private FragmentMovieDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Get movie detail from home fragment
        MovieDetailsFragmentArgs args = MovieDetailsFragmentArgs.fromBundle(getArguments());
        MovieModel movie = args.getMovieDetail();

        // Show poster image
        final ImageView poster = binding.posterImage;
        Glide.with(root.getContext())
                .load(Constants.BASE_IMAGE_URL + movie.getPoster_path())
                .into(poster);

        // Set title
        CollapsingToolbarLayout toolbarLayout =binding.toolbarLayout;
        toolbarLayout.setTitle(movie.getTitle());

        // set likes
        final TextView likeTextView = binding.content.likesTextView;
        likeTextView.setText(String.valueOf(movie.getVote_count()));

        //set rating
        final TextView ratingTextView = binding.content.ratingTextView;
        ratingTextView.setText(String.valueOf(movie.getVote_average()));

        final TextView overviewTextView = binding.content.overviewTextView;
        overviewTextView.setText(movie.getOverview());

        // set the release date
        final TextView releaseDateTextView = binding.content.releaseDateTextView;
        releaseDateTextView.setText(movie.getRelease_date());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}