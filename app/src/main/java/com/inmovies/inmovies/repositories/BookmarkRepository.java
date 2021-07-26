package com.inmovies.inmovies.repositories;

import android.app.Application;
import android.content.Context;

import com.inmovies.inmovies.database.AppDatabase;
import com.inmovies.inmovies.database.BookmarkDao;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarkRepository {

    private static BookmarkRepository instance;
    private BookmarkDao bookmarkDao;

    private BookmarkRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        bookmarkDao = db.bookmarkDao();
    }

    public static BookmarkRepository getInstance(Application application){
        if(instance == null)
            instance = new BookmarkRepository(application);
        return instance;
    }

    public Completable insert(MovieModel movieModel){
        return bookmarkDao.insert(movieModel);
    }

    public Completable deleteAll(){
        return bookmarkDao.deleteAll();
    }

    public Flowable<List<MovieModel>> getAllBookmarks(){
        return bookmarkDao.getAllBookmarks();
    }

    public Single<MovieModel> getBookmarkById(int id){
        return bookmarkDao.getBookmarkById(id);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkDao.deleteBookmarkById(id);
    }

}
