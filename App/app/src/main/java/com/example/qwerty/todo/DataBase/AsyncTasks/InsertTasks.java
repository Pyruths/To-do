package com.example.qwerty.todo.DataBase.AsyncTasks;

import android.os.AsyncTask;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

/**
 * Created by Qwerty on 25/11/2017.
 */

public class InsertTasks extends AsyncTask<Task,Void,Void> {

    private TaskDataBase database;
    public InsertTasks(TaskDataBase database){
        this.database = database;
    }

    @Override
    protected Void doInBackground(Task... tasks) {
        database.taskDAO().insertTasks(tasks);
        return null;
    }

}