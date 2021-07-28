package com.inmovies.inmovies.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.models.NowPlayingMoviesModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * DAO interface used by Room database to query the database for all now playing movies
 */
@Dao
public interface NowPlayingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(NowPlayingMoviesModel nowPlayingMoviesModel);

    @Query("SELECT * FROM movie_table JOIN now_playing_movie_table ON " +
            "now_playing_movie_table.movie_id = movie_table.id")
    Flowable<List<MovieModel>> getAllNowPlayingMovies();

    @Query("DELETE FROM now_playing_movie_table")
    Completable deleteAllNowPlayingMovies();

    @Query("SELECT * FROM movie_table JOIN now_playing_movie_table ON " +
            "now_playing_movie_table.movie_id = movie_table.id WHERE now_playing_movie_table.movie_id = :movie_id")
    Single<MovieModel> getNowPlayingMovieById(int movie_id);

}
