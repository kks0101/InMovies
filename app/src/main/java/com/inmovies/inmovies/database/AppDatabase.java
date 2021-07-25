package com.inmovies.inmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inmovies.inmovies.models.MovieModel;


@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookmarkDao bookmarkDao();

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
