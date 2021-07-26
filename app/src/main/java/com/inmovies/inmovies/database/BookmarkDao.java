package com.inmovies.inmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * DAO interface used by Room database to query the database
 */
@Dao
public interface BookmarkDao {

    @Insert()
    Completable insert(MovieModel movieModel);

    @Query("DELETE FROM movie_table")
    Completable deleteAll();

    @Query("SELECT * FROM movie_table")
    Flowable<List<MovieModel>> getAllBookmarks();

    @Query("SELECT * FROM movie_table WHERE id = :id")
    Single<MovieModel> getBookmarkById(int id);

    @Query("DELETE FROM movie_table WHERE id = :id")
    Completable deleteBookmarkById(int id);


}
