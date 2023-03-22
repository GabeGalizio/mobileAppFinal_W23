package com.example.final_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Character.class},version=1)
public abstract class CharDB extends RoomDatabase {
    public abstract CharDao charDao();

}
