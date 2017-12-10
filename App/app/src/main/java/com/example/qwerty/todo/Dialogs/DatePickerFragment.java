package com.example.qwerty.todo.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.qwerty.todo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Qwerty on 30/11/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public static String YEAR_TAG = "DatePicker_Year";
    public static String MONTH_TAG = "DatePicker_Month";
    public static String DAY_TAG = "DatePicker_Day";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c= Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle arguments = getArguments();
        if (arguments != null){
            // load data

            year = arguments.getInt(YEAR_TAG,year);
            month = arguments.getInt(MONTH_TAG,month);
            day = arguments.getInt(DAY_TAG,day);
        }

        return new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,this,year,month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView t = getActivity().findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        t.setText(new SimpleDateFormat("yyyy MMM d", Locale.US).format(c.getTime()));


        Activity activity = getActivity();
        if (activity instanceof DatePickerUser){
            ((DatePickerUser) activity).onDateSetByPicker(c);
        }

    }

    public interface DatePickerUser {
        /**
         * This is the response to the termination / submission of the widget, given back to the activity/fragment
         * that called it
         * @param c This is the date that is passed to the datePicker.
         */
        public void onDateSetByPicker(Calendar c);
    }
}

