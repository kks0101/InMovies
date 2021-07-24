package com.inmovies.inmovies.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inmovies.inmovies.adapters.RecyclerViewAdapter;
import com.inmovies.inmovies.databinding.FragmentSearchBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.utils.Constants;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private RecyclerView searchResultRecyclerView;
    private RecyclerViewAdapter searchResultRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchResultRecyclerView = binding.searchResultRecyclerview;
        searchResultRecyclerViewAdapter = new RecyclerViewAdapter();

        searchResultRecyclerView.setAdapter(searchResultRecyclerViewAdapter);
        searchResultRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));

        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.searchMovies(query, 1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchViewModel.getMoviesByQuery().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels!=null){
                    searchResultRecyclerViewAdapter.setMovieList(movieModels);
                    for(MovieModel movie: movieModels){
                        Log.v("tags ", movie.getTitle());

                    }

                }

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}