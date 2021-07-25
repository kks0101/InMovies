package com.inmovies.inmovies.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.inmovies.inmovies.ui.bookmarks.BookmarksViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(BookmarksViewModel.class)){
            return (T) new BookmarksViewModel(context);
        }
        throw new IllegalArgumentException("Unknown View Model class");
    }
}
