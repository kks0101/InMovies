package com.inmovies.inmovies.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inmovies.inmovies.R;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnMovieClickListener{

    private List<MovieModel> movieList;


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_list_item_layout,parent,false);
        return new RecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ((RecyclerViewHolder)holder).movieTitle.setText(movieList.get(position).getTitle());
        ((RecyclerViewHolder)holder).movieLikes.setText(String.valueOf(movieList.get(position).getVote_count()));

        Glide.with(holder.itemView.getContext())
                .load(Constants.BASE_IMAGE_URL + movieList.get(position).getBackdrop_path())
                .into(((RecyclerViewHolder)holder).movieImage);
    }

    @Override
    public int getItemCount() {
        if(movieList!=null)
            return movieList.size();
        return 0;
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public void onMovieClick(int position) {
        Log.v("tag", "Clicked on: " + movieList.get(position).getTitle());
    }
}
