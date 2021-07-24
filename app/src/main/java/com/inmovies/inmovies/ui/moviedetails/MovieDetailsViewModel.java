package com.inmovies.inmovies.ui.moviedetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {

    private MutableLiveData<String>  data;

    public MovieDetailsViewModel() {
        data = new MutableLiveData<>();
        data.setValue("This is movie details fragment");

    }

    public LiveData<String> getData(){
        return data;
    }
}
