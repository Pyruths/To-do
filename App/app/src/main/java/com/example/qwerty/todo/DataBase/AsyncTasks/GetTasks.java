package com.example.qwerty.todo.DataBase.AsyncTasks;

import android.os.AsyncTask;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

/**
 * Created by Qwerty on 25/11/2017.
 */

public class GetTasks extends AsyncTask<Void,Void,Task[]> {
    private TaskDataBase database;

    public GetTasks(TaskDataBase database){
        this.database = database;
    }

    @Override
    protected Task[] doInBackground(Void... voids) {
        return database.taskDAO().getAllTasks();
    }
}