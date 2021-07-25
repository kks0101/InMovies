package com.inmovies.inmovies.repositories;

import android.content.Context;

import com.inmovies.inmovies.database.BookmarkDatabase;
import com.inmovies.inmovies.database.BookmarkDbClient;
import com.inmovies.inmovies.database.BookmarkEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarkRepository {

    private static BookmarkRepository instance;
    private BookmarkDbClient bookmarkDbClient;

    private BookmarkRepository(Context context){
        BookmarkDatabase  db = BookmarkDatabase.getDatabase(context);
        bookmarkDbClient = BookmarkDbClient.getInstance(db.bookmarkDao());
    }

    public static BookmarkRepository getInstance(Context context){
        if(instance == null)
            instance = new BookmarkRepository(context);
        return instance;
    }

    public Completable insert(BookmarkEntity bookmarkEntity){
        return bookmarkDbClient.insert(bookmarkEntity);
    }

    public Completable deleteAll(){
        return bookmarkDbClient.deleteAll();
    }

    public Flowable<List<BookmarkEntity>> getAllBookmarks(){
        return bookmarkDbClient.getAllBookmarks();
    }

    public Single<BookmarkEntity> getBookmarkById(int id){
        return bookmarkDbClient.getBookmarkById(id);
    }

    public Completable deleteBookmarkById(int id){
        return bookmarkDbClient.deleteBookmarkById(id);
    }

}
