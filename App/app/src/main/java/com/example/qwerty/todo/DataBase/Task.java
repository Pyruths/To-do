package com.example.qwerty.todo.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;


/**
 * Created by Qwerty on 25/11/2017.
 * This is the representation of a Task inside of the database.
 */
/*

*/
@Entity(foreignKeys =
        @ForeignKey(entity = Task.class,
        parentColumns = "id",
        childColumns = "parent",
        onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String text;
    private int type;

    private Date expiration;

    private Date creation; // is this needed?

    // Adding a whole bunch of data for a Task

    private boolean repeating;
    // Store the ID of the parent task, allowing for multiple subTasking.

    @ColumnInfo(name = "parent")
    private Integer parent;


    @Ignore
    private boolean selected;

    public Task(){
        this.id = null;
        this.text = "";
        this.type = 0;
        this.expiration = new Date();
        this.creation = new Date();
        this.repeating = false;
        this.parent = null;
    }

    @Ignore
    public Task(Integer id,String text, int type){
        this();
        this.id = id;
        this.text = text;
        this.type = type;
    }
    @Ignore
    public Task(String text, int type){
        this();
        this.id = null;
        this.text = text;
        this.type = type;
    }

    // COPY constructor
    @Ignore
    public Task(Task t){
        this.id = t.id;
        this.text = t.text;
        this.type = t.type;
        this.expiration = t.expiration;
        this.creation = t.creation;
        this.repeating = t.repeating;
        this.parent = t.parent;
    }

    /*
    GETTERS
     */
    public boolean isRepeating() {
        return repeating;
    }

    public Integer getParent() {
        return parent;
    }

    public Date getExpiration() {
        return expiration;
    }

    public Date getCreation() {
        return creation;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*
    SETTERS
     */
    public void setType(int type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setExpiration(Date expiration) {
        //TODO need to add validation
        this.expiration = expiration;
    }
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public void setParent(Integer parent) {

        this.parent = parent == null || parent >= 0 ? parent : null;
    }

    @Ignore
    public boolean isSelected(){return selected;}

    @Ignore
    public void toggle(boolean b){this.selected = b;}


}

