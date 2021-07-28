package com.inmovies.inmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.inmovies.inmovies.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MovieModel movieModel);

    @Query("DELETE FROM movie_table")
    Completable deleteAll();

    @Query("SELECT * FROM movie_table")
    Flowable<List<MovieModel>> getAllMovies();

    @Query("SELECT * FROM movie_table WHERE id = :id")
    Single<MovieModel> getMovieById(int id);

    @Query("DELETE FROM movie_table WHERE id = :id")
    Completable deleteMovieById(int id);

}
