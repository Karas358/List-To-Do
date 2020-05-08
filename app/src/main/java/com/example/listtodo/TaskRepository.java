package com.example.listtodo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;

public class TaskRepository {
    private String DB_NAME = "my_task_db";
    private AppDB appDB;
    Context context;

    public TaskRepository(Context context){
        this.context = context;
        appDB = Room.databaseBuilder(context, AppDB.class, DB_NAME).build();
        Log.e("DB created",appDB.toString());
    }

    public void InsertTask(final Task task){
       new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                appDB.taskDoa().insertTask(task);
                return null;
            }
        }.execute();
    }

    public List<Task> getAllTasks(){
        List<Task> taskList = appDB.taskDoa().getAll();
        return taskList;
    }

    public void UpdateTask(final Task task){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                appDB.taskDoa().updateTask(task);
                return null;
            }
        }.execute();
    }

    public void DeleteTask(final Task task){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                appDB.taskDoa().deleteTask(task);
                return null;
            }
            @Override
            protected void onPostExecute(Void void_){
                super.onPostExecute(void_);
                Toast.makeText(context,task.Title + " Removed ", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
