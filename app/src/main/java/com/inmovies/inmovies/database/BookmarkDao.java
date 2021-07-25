package com.inmovies.inmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BookmarkDao {

    @Insert()
    Completable insert(BookmarkEntity bookmarkEntity);

    @Query("DELETE FROM bookmark_table")
    Completable deleteAll();

    @Query("SELECT * FROM bookmark_table")
    Flowable<List<BookmarkEntity>> getAllBookmarks();

    @Query("SELECT * FROM bookmark_table WHERE id = :id")
    Single<BookmarkEntity> getBookmarkById(int id);

    @Query("DELETE FROM bookmark_table WHERE id = :id")
    Completable deleteBookmarkById(int id);


}
