package com.example.qwerty.todo.DataBase.AsyncTasks;

import android.os.AsyncTask;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

/**
 * Created by Qwerty on 26/11/2017.
 */

public class GetSubTasks extends AsyncTask<Integer,Void,Task[]> {
    private TaskDataBase database;

    public GetSubTasks(TaskDataBase database){
        this.database = database;
    }

    @Override
    protected Task[] doInBackground(Integer... id) {
        return database.taskDAO().getSubTasks(id[0]);
    }
}
