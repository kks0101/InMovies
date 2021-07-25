package com.inmovies.inmovies.ui.bookmarks;

import android.content.Context;
import androidx.lifecycle.ViewModel;

import com.inmovies.inmovies.database.BookmarkEntity;
import com.inmovies.inmovies.repositories.BookmarkRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarksViewModel extends ViewModel {

    private BookmarkRepository bookmarkRepository;

    public BookmarksViewModel(Context context) {
        bookmarkRepository = BookmarkRepository.getInstance(context);
    }

    public Completable insert(BookmarkEntity bookmarkEntity){
        return bookmarkRepository.insert(bookmarkEntity);
    }

    public Completable deleteAll(){
        return bookmarkRepository.deleteAll();
    }

    public Flowable<List<BookmarkEntity>> getAllBookmarks(){
        return bookmarkRepository.getAllBookmarks();
    }

    public Single<BookmarkEntity> getBookmarkById(int id){
        return bookmarkRepository.getBookmarkById(id);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkRepository.deleteBookmarkById(id);
    }
}