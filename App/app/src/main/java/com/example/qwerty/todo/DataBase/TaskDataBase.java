package com.example.qwerty.todo.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Qwerty on 25/11/2017.
 * This is a room persistent design.
 * This implements a singleton design for the database and should be used in conjuction with
 * AsyncTask.
 */

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDataBase extends RoomDatabase {
    private static TaskDataBase INSTANCE;

    public abstract TaskDAO taskDAO();

    //enforce singleton design
    public static TaskDataBase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,TaskDataBase.class,"taskdatabase").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
