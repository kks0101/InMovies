package com.inmovies.inmovies.database;

import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class DatabaseClient {

    private final BookmarkDao bookmarkDao;
    private static DatabaseClient instance;

    public static DatabaseClient getInstance(BookmarkDao  bookmarkDao){
        if(instance == null){
            instance = new DatabaseClient(bookmarkDao);
        }
        return instance;
    }

    private DatabaseClient(BookmarkDao bookmarkDao){
        this.bookmarkDao = bookmarkDao;
    }

    public Flowable<List<MovieModel>> getAllBookmarks(){
        return bookmarkDao.getAllBookmarks();
    }

    public Single<MovieModel> getBookmarkById(int id){
        return bookmarkDao. getBookmarkById(id);
    }

    public Completable insert(MovieModel MovieModel){
        return bookmarkDao.insert(MovieModel);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkDao.deleteBookmarkById(id);
    }

    public Completable deleteAll(){
        return bookmarkDao.deleteAll();
    }

}
