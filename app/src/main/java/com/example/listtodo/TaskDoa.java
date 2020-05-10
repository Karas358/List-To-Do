package com.example.listtodo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDoa {

    @Query("SELECT * FROM task_table")
    List<Task> getAll();

    @Query("Delete from task_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);


}
