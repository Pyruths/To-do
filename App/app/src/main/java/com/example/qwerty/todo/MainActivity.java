package com.example.qwerty.todo;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.concurrent.ExecutionException;

/**
 * Created by Qwerty on 25/11/2017.
 * This is the main task list page.
 */
public class MainActivity extends AppCompatActivity {
    private TaskDataBase mDataBase;
    private Task[] mTasks;
    private Button notif;
    private NotificationManager mNotifier;
    private NotificationCompat.Builder notifBuilder;
    private static final String uniqueID = "989";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SETTING UP NOTIFICATION CHANNEL
        mNotifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(uniqueID,"MyApp",NotificationManager.IMPORTANCE_DEFAULT);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mNotifier.createNotificationChannel(mChannel);

        //Making notification builder
        notifBuilder = new NotificationCompat.Builder(this,uniqueID);


        notif = findViewById(R.id.notification_button);



        //set up database to be used for the first time
        mDataBase = TaskDataBase.getDatabase(getApplicationContext());

        mTasks = null;
        try{
            mTasks = new GetTasks(mDataBase).execute().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment fragment = TaskListView.newInstance(null); // null for all
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

    public void notif(View v){
        notifBuilder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New notification")
                .setContentText("This is a update");

        Notification notification = notifBuilder.build();
        mNotifier.notify(1,notification);
    }

}


