package com.kotiyaltech.craftsbeer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kotiyaltech.craftsbeer.model.BeerItem;

/**
 * Created by hp pc on 30-06-2018.
 */

@Database(entities = {BeerItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract BeerDao beerDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "beerDatabase").build();
        }
        return INSTANCE;
    }

}
