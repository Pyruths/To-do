package com.example.qwerty.todo.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

/**
 * Created by Qwerty on 25/11/2017.
 * This is the interface used to interact with the Task table.
 */

@Dao
public interface TaskDAO {

    @Insert
    void insertTasks(Task... t);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTasks(Task... t);

    @Delete
    void deleteTasks(Task... t);

    @Query("SELECT * FROM Task")
    Task[] getAllTasks();

    @Query("SELECT * FROM Task where id = :taskID")
    Task[] getTask(int taskID);

    @Query("SELECT * FROM Task where parent = :taskID")
    Task[] getSubTasks(int taskID);
}

