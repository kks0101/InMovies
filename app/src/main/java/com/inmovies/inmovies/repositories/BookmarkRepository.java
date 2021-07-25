package com.inmovies.inmovies.repositories;

import android.content.Context;

import com.inmovies.inmovies.database.AppDatabase;
import com.inmovies.inmovies.database.DatabaseClient;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookmarkRepository {

    private static BookmarkRepository instance;
    private DatabaseClient databaseClient;

    private BookmarkRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        databaseClient = DatabaseClient.getInstance(db.bookmarkDao());
    }

    public static BookmarkRepository getInstance(Context context){
        if(instance == null)
            instance = new BookmarkRepository(context);
        return instance;
    }

    public Completable insert(MovieModel movieModel){
        return databaseClient.insert(movieModel);
    }

    public Completable deleteAll(){
        return databaseClient.deleteAll();
    }

    public Flowable<List<MovieModel>> getAllBookmarks(){
        return databaseClient.getAllBookmarks();
    }

    public Single<MovieModel> getBookmarkById(int id){
        return databaseClient.getBookmarkById(id);
    }

    public Completable deleteBookmarkById(int id){
        return databaseClient.deleteBookmarkById(id);
    }

}
