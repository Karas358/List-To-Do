package com.example.listtodo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "subtitle")
    public String SubTitle;

    @ColumnInfo(name = "completed")
    public boolean Completed;

    public Task(){

    }

    public Task(String Title, String Subtitle, boolean Completed){
        this.Title = Title;
        this.SubTitle = Subtitle;
        this.Completed = Completed;
    }

}
