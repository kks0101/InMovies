package com.inmovies.inmovies.ui.bookmarks;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.repositories.BookmarkRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarksViewModel extends ViewModel {

    private BookmarkRepository bookmarkRepository;

    public BookmarksViewModel(Application application) {
        bookmarkRepository = BookmarkRepository.getInstance(application);
    }

    public Completable insert(MovieModel movieModel){
        return bookmarkRepository.insert(movieModel);
    }

    public Completable deleteAll(){
        return bookmarkRepository.deleteAll();
    }

    public Flowable<List<MovieModel>> getAllBookmarks(){
        return bookmarkRepository.getAllBookmarks();
    }

    public Single<MovieModel> getBookmarkById(int id){
        return bookmarkRepository.getBookmarkById(id);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkRepository.deleteBookmarkById(id);
    }
}