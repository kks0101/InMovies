package com.inmovies.inmovies.ui.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    Application application;

    public ViewModelFactory(Application application){
        this.application = application;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(BookmarksViewModel.class)){

            return (T) new BookmarksViewModel(application);
        }
        else
            throw new IllegalArgumentException("View model class unknown");

    }
}
