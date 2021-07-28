package com.inmovies.inmovies.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.inmovies.inmovies.R;
import com.inmovies.inmovies.databinding.FragmentMovieDetailsBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.ui.bookmarks.BookmarksViewModel;
import com.inmovies.inmovies.ui.bookmarks.ViewModelFactory;
import com.inmovies.inmovies.utils.Constants;

import org.jetbrains.annotations.NotNull;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsFragment extends Fragment {

    private FragmentMovieDetailsBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private BookmarksViewModel bookmarksViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Get movie detail from home fragment
        MovieDetailsFragmentArgs args = MovieDetailsFragmentArgs.fromBundle(getArguments());
        MovieModel movie = args.getMovieDetail();

        // Show poster image
        final ImageView poster = binding.posterImage;
        Glide.with(root.getContext())
                .load(Constants.BASE_IMAGE_URL + movie.getPoster_path())
                .into(poster);

        // Set title
        CollapsingToolbarLayout toolbarLayout =binding.toolbarLayout;
        toolbarLayout.setTitle(movie.getTitle());

        // set likes
        final TextView likeTextView = binding.content.likesTextView;
        likeTextView.setText(String.valueOf(movie.getVote_count()));

        //set rating
        final TextView ratingTextView = binding.content.ratingTextView;
        ratingTextView.setText(String.valueOf(movie.getVote_average()));

        //set overview
        final TextView overviewTextView = binding.content.overviewTextView;
        overviewTextView.setText(movie.getOverview());

        // set the release date
        final TextView releaseDateTextView = binding.content.releaseDateTextView;
        releaseDateTextView.setText(movie.getRelease_date());
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        bookmarksViewModel =
                new ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel.class);

        final FloatingActionButton addBookmark = binding.bookmark;
        final FloatingActionButton shareButton = binding.shareButton;


        // Implementing sharing of movie feature
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String shareText = "Have you seen " + movie.getTitle() + "? Check it out now!!\n";
                // generating deep link for this movie
                String link = "http://www.inmovies.com/movie?movie_id=" + movie.getId();
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText + link);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        // should be modified: going by this approach for now
        // Check if this movie as already been bookmarked:
        // update the bookmark button image accordingly
        bookmarksViewModel.getBookmarkById(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull MovieModel movieModel) {
                        addBookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_beenhere_24));
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        addBookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_bookmark_24));
                    }
                });


        addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //disable bookmark button, until bookmark database transaction done
                addBookmark.setEnabled(false);
                // check if this movie is already added to Bookmark:
                // if yes -> remove it from the bookmark
                // if no -> add it to bookmark
                bookmarksViewModel.getBookmarkById(movie.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<MovieModel>() {
                            @Override
                            public void onSubscribe(@NotNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NotNull MovieModel movieModel) {
                                disposable.add(bookmarksViewModel.deleteBookmarkById(movie.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(()->{

                                    Snackbar.make(root, "Bookmark removed", Snackbar.LENGTH_LONG).show();
                                    addBookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                                            R.drawable.ic_bookmark_24));
                                },
                                        throwable -> {
                                            Log.e("bookmark", "Error occurred while deleting bookmark" + throwable.getMessage());
                                        }));
                                addBookmark.setEnabled(true);
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {
                                Log.v("bookmark", "trying to add bookmark");
                                disposable.add(bookmarksViewModel.insert(movie.getId())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(()->{
                                                    addBookmark.setEnabled(true);
                                                    Snackbar.make(root, "Bookmark added", Snackbar.LENGTH_LONG).show();
                                                    addBookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                                                            R.drawable.ic_baseline_beenhere_24));
                                                },
                                                throwable -> {
                                                    addBookmark.setEnabled(true);
                                                    Log.e("bookmark", "Unable to add bookmark" + throwable.getMessage());
                                                }));
                            }
                        });


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