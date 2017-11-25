package com.example.qwerty.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.concurrent.ExecutionException;

public class newTask extends AppCompatActivity {
    public static final String TASK_STRING = "com.example.qwerty.todo.TASK";
    private TaskDataBase mdatabase;
    private EditText text;
    private Task t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mdatabase = TaskDataBase.getDatabase(getApplicationContext());
        text = findViewById(R.id.editText);

        Intent intent = getIntent();
        int taskID = intent.getIntExtra(TASK_STRING,-1);

        t = new Task();
        if (taskID == -1){
            return; // Task does not exist.
        }

        //look through database
        Task[] tasks = null;
        try{
            tasks = new GetTask(mdatabase).execute(taskID).get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }


        if (tasks == null || tasks.length == 0) {
            return; // Task does not exist
        }
        t = tasks[0];
        t.setText(tasks[0].getText()); // This line might not be needed
        text.setText(tasks[0].getText());

    }

    /**
     * This saves the inputted text and returns to the task list
     * @param v the submit button itself
     */
    public void Submit(View v){
        t.setText(text.getText().toString());
        new SaveTask(mdatabase).execute(t);
        onNavigateUp();
    }


    private static class GetTask extends AsyncTask<Integer,Void,Task[]> {
        private TaskDataBase database;

        GetTask(TaskDataBase database){
            this.database = database;
        }

        @Override
        protected Task[] doInBackground(Integer... id) {
            return database.taskDAO().getTask(id[0]);
        }
    }
    private static class SaveTask extends AsyncTask<Task,Void,Void> {
        private TaskDataBase database;

        SaveTask(TaskDataBase database){
            this.database = database;
        }

        @Override
        protected Void doInBackground(Task... task) {
            // run a check to see if its needs to be added in or not
            if (task[0].getId() == null)
            {
                database.taskDAO().insertTask(task[0]);
            }
            else{
                database.taskDAO().updateTask(task[0]);
            }

            return null;
        }
    }
}
