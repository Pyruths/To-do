package com.example.qwerty.todo.DataBase.AsyncTasks;

import android.os.AsyncTask;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

/**
 * Created by Qwerty on 25/11/2017.
 */

public class GetTask extends AsyncTask<Integer,Void,Task[]> {
    private TaskDataBase database;

    public GetTask(TaskDataBase database){
        this.database = database;
    }

    @Override
    protected Task[] doInBackground(Integer... id) {
        return database.taskDAO().getTask(id[0]);
    }
}