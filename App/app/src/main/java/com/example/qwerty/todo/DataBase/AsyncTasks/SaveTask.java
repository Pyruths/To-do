package com.example.qwerty.todo.DataBase.AsyncTasks;

import android.os.AsyncTask;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

/**
 * Created by Qwerty on 25/11/2017.
 */

public class SaveTask extends AsyncTask<Task,Void,Void> {
    private TaskDataBase database;

    public SaveTask(TaskDataBase database){
        this.database = database;
    }

    @Override
    protected Void doInBackground(Task... task) {
        // run a check to see if its needs to be added in or not
        if (task[0].getId() == null)
        {
            database.taskDAO().insertTasks(task);
        }
        else{
            database.taskDAO().updateTasks(task);
        }

        return null;
    }
}