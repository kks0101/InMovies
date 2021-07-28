package com.inmovies.inmovies.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.inmovies.inmovies.ui.bookmarks.BookmarksViewModel;

import org.jetbrains.annotations.NotNull;


/**
 * We need ViewModelFactory because accessing the the Singleton Database object
 * requires application reference, and since ViewModelProvider does not allow creating
 * view model with args constructor, this class facilitates that use case
 */
public class SearchViewModelFactory implements ViewModelProvider.Factory {

    Application application;

    public SearchViewModelFactory(Application application){
        this.application = application;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)){

            return (T) new SearchViewModel(application);
        }
        else
            throw new IllegalArgumentException("View model class unknown");

    }
}
