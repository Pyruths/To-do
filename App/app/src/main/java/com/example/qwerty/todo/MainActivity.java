package com.example.qwerty.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView toDoList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskDataBase mdatabase;
    private Task mtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up database
        mdatabase = TaskDataBase.getDatabase(getApplicationContext());

        // add initial data  TODO(this should be removed)
        addData(mdatabase);

        toDoList = findViewById(R.id.toDoList);

        mLayoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(mLayoutManager);

        Task[] tasks = null;
        try{
            tasks = new GetTasks(mdatabase).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        mAdapter = new TodoItemAdapter(tasks);
        toDoList.setAdapter(mAdapter);


    }
    public void NewTask(View v){
        Intent intent = new Intent(this,newTask.class);
        startActivity(intent);

    }





    public void addData(TaskDataBase database){
        Task[] tasks = null;
        try{
             tasks = new GetTasks(database).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }


        if (tasks != null && tasks.length == 0){
            Task[] list = {new Task("task 1", 1),
                    new Task("task 2", 2),
                    new Task("task 3", 1),
                    new Task("task 4", 1),
                    new Task("task 5", 3),
                    new Task("task 6", 1)};
            //make a thing to perform stuff in background. Insert is not automatically asynchronous.
            new InsertTasks(database).execute(list);
        }

    }

    // These async tasks are to interface with the DAO
    // TODO move these else where and make a generic async task to handle all room tasks.
    private static class GetTasks extends AsyncTask<Void,Void,Task[]>{
        private TaskDataBase database;

        GetTasks(TaskDataBase database){
            this.database = database;
        }

        @Override
        protected Task[] doInBackground(Void... voids) {
            return database.taskDAO().getAllTasks();
        }
    }
    private static class InsertTasks extends AsyncTask<Task,Void,Void>{

        private TaskDataBase database;
        InsertTasks(TaskDataBase database){
            this.database = database;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            for( Task t : tasks){
                database.taskDAO().insertTask(t);
            }
            return null;
        }

    }

}


