package com.example.qwerty.todo.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.qwerty.todo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Qwerty on 1/12/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static String MINUTE_TAG = "TimePicker_Minute";
    public static String HOUR_TAG = "TimePicker_Hour";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c= Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        Bundle arguments = getArguments();
        if (arguments != null){
            // load data
            hour = arguments.getInt(HOUR_TAG,hour);
            minute = arguments.getInt(MINUTE_TAG,minute);
        }

        return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,this,hour,minute,false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        TextView t = getActivity().findViewById(R.id.time); // this makes it specific to taskView (has the date tag)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR,hour);
        c.set(Calendar.MINUTE,minute);
        t.setText(new SimpleDateFormat("hh:mm a", Locale.US).format(c.getTime()));

        Activity activity = getActivity();
        if (activity instanceof TimePickerUser){
            ((TimePickerUser) activity).onTimeSetByPicker(c);
        }
    }

    public interface TimePickerUser {
        /**
         * This is the response to the termination / submission of the widget, given back to the activity/fragment
         * that called it
         * @param c This is the date that is passed to the datePicker.
         */
        public void onTimeSetByPicker(Calendar c);
    }
}
