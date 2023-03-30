package com.example.final_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CharDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Character character);
    @Query("select * from charTable")
    List<Character> getAllChars();
    @Delete
    int delete(Character character);
    @Update
    int update(Character character);

}
