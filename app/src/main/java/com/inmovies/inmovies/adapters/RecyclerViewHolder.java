package com.inmovies.inmovies.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inmovies.inmovies.R;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView movieTitle;
    TextView movieLikes;
    ImageView movieImage;
    private OnMovieClickListener onMovieClickListener;


    public RecyclerViewHolder(@NonNull @NotNull View itemView, OnMovieClickListener onMovieClickListener) {
        super(itemView);

        movieTitle = itemView.findViewById(R.id.movie_title);
        movieLikes = itemView.findViewById(R.id.movie_likes);
        movieImage = itemView.findViewById(R.id.movie_image);
        this.onMovieClickListener = onMovieClickListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onMovieClickListener.onMovieClick(getBindingAdapterPosition());
    }
}
