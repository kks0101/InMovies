package com.inmovies.inmovies.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inmovies.inmovies.R;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView movieTitle;
    TextView movieLikes;
    ImageView movieImage;

    public RecyclerViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        movieTitle = itemView.findViewById(R.id.movie_title);
        movieLikes = itemView.findViewById(R.id.movie_likes);
        movieImage = itemView.findViewById(R.id.movie_image);


    }
}
