package com.example.qwerty.todo.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.util.List;

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
    public static enum REPEAT_TYPE {None,Daily,Weekly,BiWeekly,Monthly,Yearly}

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String text;
    private int type;

    private Date expiration;

    private Date creation; // is this needed?

    // Adding a whole bunch of data for a Task

    private boolean repeating;
    /**
     * This will dictate the repeating type. It will change what the 'repeatingDates' mean
     * Daily - repeatingDates means nothing, and should be None, as it is daily repeating.
     * Weekly - repeatingDates will have up to 7 numbers, running Monday to Sunday
     * BiWeekly - repeatingDates will haev up to 14 numbers, running Monday to 2nd Sunday. The first week of the year is always considered 0-7. This will need an extra counter
     * Monthly - reapeatingDates will have up to 31 numbers, days of the month. If a day is over the
     *          max days of that month, it will just assume end of the month. If there are multiple copies after, they are ignored
     * Yearly - can have up to 365 Unique numbers, ( generally will just be one).
     */
    @Ignore
    private REPEAT_TYPE repeatType;

    /**
     * In the database, this is converted to a String
     * TODO potentially move this, as this doesn't need to be gotten every time we need it.
     * @see com.example.qwerty.todo.DataBase.Converters.ListIntegerConverter
     */
    @Ignore
    private List<Integer> repeatingDates;



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

