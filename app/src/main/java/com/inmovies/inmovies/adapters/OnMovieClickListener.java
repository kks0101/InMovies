package com.inmovies.inmovies.adapters;

import android.view.View;

/**
 * used to implement on click behavior on each recycler view item
 */
public interface OnMovieClickListener {
    void onMovieClick(int position, View view);
}
