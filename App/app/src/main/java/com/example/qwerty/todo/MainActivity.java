package com.example.qwerty.todo;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.concurrent.ExecutionException;

/**
 * Created by Qwerty on 25/11/2017.
 * This is the main task list page.
 */
public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
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

        mTasks = null;
        try{
            mTasks = new GetTasks(mDataBase).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        mFragmentManager = getFragmentManager();
        if(savedInstanceState == null){

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            Fragment fragment = TaskListView.newInstance(-1); // -1 for get all
            fragmentTransaction.add(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addTask:
                Intent intent = new Intent(this,TaskView.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void NewTask(View v){
        Intent intent = new Intent(this,TaskView.class);
        startActivity(intent);

    }

    public void DeleteAll(View v){
        TaskListView f = (TaskListView) mFragmentManager.findFragmentById(R.id.fragment_container);
        f.deleteAll();

    }
}


