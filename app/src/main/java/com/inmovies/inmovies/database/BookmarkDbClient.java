package com.inmovies.inmovies.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarkDbClient {

    private final BookmarkDao bookmarkDao;
    private static BookmarkDbClient instance;

    public static BookmarkDbClient getInstance(BookmarkDao  bookmarkDao){
        if(instance == null){
            instance = new BookmarkDbClient(bookmarkDao);
        }
        return instance;
    }

    private BookmarkDbClient(BookmarkDao bookmarkDao){
        this.bookmarkDao = bookmarkDao;
    }

    public Flowable<List<BookmarkEntity>> getAllBookmarks(){
        return bookmarkDao.getAllBookmarks();
    }

    public Single<BookmarkEntity> getBookmarkById(int id){
        return bookmarkDao. getBookmarkById(id);
    }

    public Completable insert(BookmarkEntity bookmarkEntity){
        return bookmarkDao.insert(bookmarkEntity);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkDao.deleteBookmarkById(id);
    }

    public Completable deleteAll(){
        return bookmarkDao.deleteAll();
    }

}
