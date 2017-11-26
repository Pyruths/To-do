package com.example.qwerty.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.qwerty.todo.DataBase.AsyncTasks.DeleteTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView toDoList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskDataBase mDataBase;
    private Task[] mTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up database to be used for the first time
        mDataBase = TaskDataBase.getDatabase(getApplicationContext());

        toDoList = findViewById(R.id.toDoList);

        mLayoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(mLayoutManager);

        mTasks = null;
        try{
            mTasks = new GetTasks(mDataBase).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        mAdapter = new TodoItemAdapter(mTasks);
        toDoList.setAdapter(mAdapter);


    }
    public void NewTask(View v){
        Intent intent = new Intent(this,TaskView.class);
        startActivity(intent);

    }

    //TODO CLEAN THIS UP, it shouldn't have to refresh the app to get the recyclerView to update
    //Possibly have a better structure than iterating through task list.
    public void DeleteAll(View v){
        // Iterate through everything that is selected
        ArrayList<Task> h = new ArrayList<>();
        for (Task t : mTasks){
            if(t.isSelected()){
                h.add(t);
            }
        }

        // Turn it into an array that is processable by the Async Task
        Task[] tasklist = new Task[h.size()];
        new DeleteTasks(mDataBase).execute(h.toArray(tasklist));

        //Refresh
        //mAdapter.notifyDataSetChanged();
        recreate();

    }
}


