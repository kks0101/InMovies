package com.inmovies.inmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {BookmarkEntity.class}, version = 1, exportSchema = false)
public abstract class BookmarkDatabase extends RoomDatabase {

    public abstract BookmarkDao bookmarkDao();

    private static volatile BookmarkDatabase INSTANCE;


    public static BookmarkDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (BookmarkDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookmarkDatabase.class, "bookmark_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
