package com.inmovies.inmovies.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.models.PopularMoviesModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PopularMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PopularMoviesModel popularMoviesModel);

    @Query("SELECT * FROM movie_table JOIN popular_movie_table ON " +
            "popular_movie_table.movie_id = movie_table.id")
    Flowable<List<MovieModel>> getAllPopularMovies();

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    Completable deleteAllPopularMovies();

    @Query("SELECT * FROM movie_table JOIN popular_movie_table ON " +
            "popular_movie_table.movie_id = movie_table.id WHERE popular_movie_table.movie_id = :movie_id")
    Single<MovieModel> getPopularMovieById(int movie_id);

}
