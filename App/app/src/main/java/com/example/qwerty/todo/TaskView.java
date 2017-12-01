package com.example.qwerty.todo;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qwerty.todo.DataBase.AsyncTasks.DeleteTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetSubTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTask;
import com.example.qwerty.todo.DataBase.AsyncTasks.SaveTask;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    private TextView dateText;
    private TextView timeText;
    private TextView parentBox;
    private Task t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        //SET VIEWS
        mDatabase = TaskDataBase.getDatabase(getApplicationContext());
        text = findViewById(R.id.editText);
        parentBox = findViewById(R.id.parent);
        dateText = findViewById(R.id.date);
        timeText = findViewById(R.id.time);




        // GET ARGUMENTS
        Intent intent = getIntent();
        int taskID = intent.getIntExtra(TASK_STRING,-1);
        Integer taskParent = intent.getIntExtra(TASK_PARENT,-1);

        //INITALISE CONTENT
        t = new Task();
        t.setParent(taskParent); // Set Parent ID -1 will set to null

        Task[] tasks = null;
        //CONDITIONALS
        if (taskID != -1){
            try{
                tasks = new GetTask(mDatabase).execute(taskID).get();
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }

        }

        // get parents task
        if(tasks != null){
            t = tasks[0];
            // get subtasks
            mTasks = null;
            try{
                mTasks = new GetSubTasks(mDatabase).execute(taskID).get();
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }


        //SET UI ELEMENTS

        parentBox.setText(String.valueOf(taskParent));
        text.setText(t.getText());

        //TODO turn these patterns into a String resource or something to reference later.
        dateText.setText(new SimpleDateFormat("yyyy MMM d", Locale.US).format(t.getExpiration()));
        timeText.setText(new SimpleDateFormat("hh:mm a", Locale.US).format(t.getExpiration()));

        final Date d = t.getExpiration();

        dateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment f = new DatePickerFragment();
                if (d != null){
                    Bundle b = new Bundle();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    b.putInt(DatePickerFragment.YEAR_TAG,c.get(Calendar.YEAR));
                    b.putInt(DatePickerFragment.MONTH_TAG,c.get(Calendar.MONTH));
                    b.putInt(DatePickerFragment.DAY_TAG,c.get(Calendar.DAY_OF_MONTH));
                    f.setArguments(b);
                }
                f.show(getFragmentManager(),"timePicker");

            }
        });

        timeText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment f = new TimePickerFragment();
                if (d != null){
                    Bundle b = new Bundle();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    b.putInt(TimePickerFragment.HOUR_TAG,c.get(Calendar.HOUR));
                    b.putInt(TimePickerFragment.MINUTE_TAG,c.get(Calendar.MINUTE));
                    f.setArguments(b);
                }
                f.show(getFragmentManager(),"timePicker");

            }
        });

        if(savedInstanceState == null && t.getId() != null) {
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
            return;
        }

        //PROBLEM, showing all tasks.
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
