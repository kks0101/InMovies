package com.inmovies.inmovies.ui.bookmarks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inmovies.inmovies.adapters.RecyclerViewAdapter;
import com.inmovies.inmovies.databinding.FragmentBookmarksBinding;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.repositories.ViewModelFactory;
import com.inmovies.inmovies.utils.Constants;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BookmarksFragment extends Fragment {

    private BookmarksViewModel bookmarksViewModel;
    private FragmentBookmarksBinding binding;
    private RecyclerView bookmarkRecyclerView;
    private RecyclerViewAdapter bookmarkRecyclerViewAdapter;

    private final CompositeDisposable disposable = new CompositeDisposable();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        ViewModelFactory viewModelFactory = new ViewModelFactory(getContext());

        bookmarksViewModel =
                new ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel.class);


        binding = FragmentBookmarksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bookmarkRecyclerView = binding.bookmarkRecyclerview;
        bookmarkRecyclerViewAdapter = new RecyclerViewAdapter();
        bookmarkRecyclerView.setAdapter(bookmarkRecyclerViewAdapter);
        bookmarkRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));

        getAllBookmarks();
        return root;
    }

    public void getAllBookmarks(){

        // Using RxJava to get details from Bookmark Database

        //ArrayList<MovieModel> bookmarkList = new ArrayList<>();
        disposable.add(bookmarksViewModel.getAllBookmarks()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieList -> {
            bookmarkRecyclerViewAdapter.setMovieList(movieList);
            for(MovieModel movie: movieList){
                Log.v("bookmark", movie.getTitle());
                //bookmarkList.add(new MovieModel(bookmark));

            }



                },throwable -> Log.e("bookmark", "Unable to get bookmarks", throwable)
                ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}