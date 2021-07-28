package com.inmovies.inmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inmovies.inmovies.models.BookmarkModel;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.models.NowPlayingMoviesModel;
import com.inmovies.inmovies.models.PopularMoviesModel;

/**
 * Singleton class to get instance of database to interact with different table
 * in the database
 */
@Database(entities = {MovieModel.class, BookmarkModel.class, NowPlayingMoviesModel.class,
        PopularMoviesModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookmarkDao bookmarkDao();

    public abstract MovieDao movieDao();
    public abstract NowPlayingMovieDao nowPlayingMovieDao();
    public abstract PopularMovieDao popularMovieDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bookmark_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
