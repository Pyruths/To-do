package com.example.qwerty.todo.DataBase.Converters;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Qwerty on 8/12/2017.
 */

public class ListIntegerConverter {
    @TypeConverter
    public static List<Integer> toListInteger(String text){
        if (text == null)
            return null;
        String[] strings = text.split(",");
        List<Integer> list = new ArrayList<>();
        for (String i : strings){
            list.add(Integer.getInteger(i));
        }
        return list;
    }

    @TypeConverter
    public static String toIntegerString(List<Integer> list){
        if (list == null)
            return null;
        StringBuilder s = new StringBuilder();

        for (Integer i : list){
            s.append(i);
            s.append('s'); // this leaves an additional , at the end.
        }
        return s.toString();

    }
}
