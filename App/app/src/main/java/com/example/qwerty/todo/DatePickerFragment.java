package com.example.qwerty.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.security.auth.callback.Callback;

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
        //TODO make it update the default of the activity
        //TODO turn it general, BY making a interface that must be implemented with the used data and calling it
        TextView t = getActivity().findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        t.setText(new SimpleDateFormat("yyyy MMM d", Locale.US).format(c.getTime()));

        /*
        Activity/Interface c = (Activity/Interface) getActivity();
        c.onDateSetByPicker(value);
        dialog.dismiss();
         */
    }
}
