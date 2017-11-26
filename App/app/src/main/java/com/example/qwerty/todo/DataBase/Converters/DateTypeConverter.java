package com.example.qwerty.todo.DataBase.Converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Qwerty on 25/11/2017.
 */

public class DateTypeConverter{
    @TypeConverter
    public static Date toDate(Long value){
        return value == null? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date date){
        return date == null? null : date.getTime();
    }
}