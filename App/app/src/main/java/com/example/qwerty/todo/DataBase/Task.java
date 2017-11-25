package com.example.qwerty.todo.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Qwerty on 25/11/2017.
 * This is the representation of a Task inside of the database.
 */

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String text;
    private int type;

    public Task(){
        this.id = null;
        this.text = "";
        this.type = 0;
    }

    public Task(Integer id,String text, int type){
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public Task(String text, int type){
        this.id = null;
        this.text = text;
        this.type = type;
    }

    // COPY constructor
    public Task(Task t){
        this.id = t.id;
        this.text = t.text;
        this.type = t.type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;

    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }
}
