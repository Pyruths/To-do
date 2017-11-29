package com.example.qwerty.todo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qwerty.todo.DataBase.AsyncTasks.DeleteTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetSubTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTask;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.SaveTask;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.concurrent.ExecutionException;

/**
 * Created by Qwerty on 25/11/2017.
 * This is a view of an individual task
 */
public class TaskView extends AppCompatActivity {
    public static final String TASK_STRING = "com.example.qwerty.todo.TASK";
    public static final String TASK_PARENT = "com.example.qwerty.todo.PARENT";
    private Task[] mTasks;
    private TaskDataBase mDatabase;
    private EditText text;
    private TextView parentBox;
    private Task t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mDatabase = TaskDataBase.getDatabase(getApplicationContext());
        text = findViewById(R.id.editText);
        parentBox = findViewById(R.id.parent);

        Intent intent = getIntent();
        int taskID = intent.getIntExtra(TASK_STRING,-1);
        Integer taskParent = intent.getIntExtra(TASK_PARENT,-1);
        t = new Task();

        t.setParent(taskParent); // Set Parent ID -1 will set to null

        parentBox.setText(String.valueOf(taskParent));
        if (taskID == -1){
            return; // Task does not exist.
        }
        //look through database
        Task[] tasks = null;
        try{
            tasks = new GetTask(mDatabase).execute(taskID).get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        if (tasks == null || tasks.length == 0) {
            return; // Task does not exist
        }
        t = tasks[0];
        t.setText(tasks[0].getText()); // This line might not be needed
        text.setText(tasks[0].getText());

        mTasks = null;
        try{
            mTasks = new GetSubTasks(mDatabase).execute(taskID).get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        if(savedInstanceState == null) {
            // Adding in fragment into the display with arguments
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = TaskListView.newInstance(t.getId());
            fragmentTransaction.add(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:

                onNavigateUp();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This saves the inputted text and returns to the task list
     * @param v the submit button itself
     */
    public void Submit(View v){
        if (text.getText().length() == 0){ return;}
        t.setText(text.getText().toString());
        new SaveTask(mDatabase).execute(t);
        onNavigateUp();
    }

    public void Delete(View v){
        //ID has been preset beforehand, so all we need to do is pass it in
        new DeleteTasks(mDatabase).execute(t);
        onNavigateUp();
    }

    public void AddSubTask(View v){
        if (t.getId() == null){
            Snackbar.make(v,"Submit your task first",Snackbar.LENGTH_SHORT).show();
            return; // TODO make this show a snackbar message
        }

        Intent intent = new Intent(this,TaskView.class);
        intent.putExtra(TaskView.TASK_PARENT,t.getId());
        startActivity(intent);
    }

    @Override
    public boolean onNavigateUp() {
        //TODO there is an error here on entering a 'new' task.

        if (t.getParent() != null){
            finish();
            return true;
        }
        return super.onNavigateUp();
    }

}
