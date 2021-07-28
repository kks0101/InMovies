package com.inmovies.inmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.inmovies.inmovies.models.BookmarkModel;
import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * DAO interface used by Room database to query the database for bookmarks
 */
@Dao
public interface BookmarkDao {

    @Insert()
    Completable insert(BookmarkModel bookmarkModel);

    @Query("SELECT * FROM bookmark_table JOIN movie_table ON bookmark_table.movie_id = movie_table.id")
    Flowable<List<MovieModel>> getAllBookmarks();

    @Query("DELETE FROM bookmark_table")
    Completable deleteAllBookmarks();

    @Query("DELETE FROM bookmark_table WHERE movie_id = :movie_id")
    Completable deleteBookmarkById(int movie_id);

    @Query("SELECT * FROM movie_table JOIN bookmark_table ON " +
            "bookmark_table.movie_id = movie_table.id WHERE bookmark_table.movie_id = :movie_id")
    Single<MovieModel> getBookmarkById(int movie_id);
}
