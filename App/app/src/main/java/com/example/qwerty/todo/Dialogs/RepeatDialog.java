package com.example.qwerty.todo.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.qwerty.todo.R;

import java.util.Calendar;

/**
 * Created by Qwerty on 3/12/2017.
 */

public class RepeatDialog extends Dialog {

    private Activity c;
    private Button cancelButton, submitButton;
    private OnRepeatSetListener repeat;
    private NumberPicker picker;
    public RepeatDialog(Activity activity){
        super(activity);
        this.c = activity;
        this.repeat = null;
    }

    public RepeatDialog(Activity activity,OnRepeatSetListener listener){
        this(activity);
        this.repeat = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.repeat_dialog);

        cancelButton = findViewById(R.id.cancel);
        submitButton = findViewById(R.id.submit);
        picker = findViewById(R.id.custom);
        picker.setMinValue(0);
        picker.setMaxValue(365);
        picker.setWrapSelectorWheel(false);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(view);
            }
        });


    }

    public void cancel(View v){
        this.dismiss();
    }

    public void submit(View v){

        if (repeat != null){

            repeat.onRepeatSet(10);
        }
        this.dismiss();

    }

    public interface OnRepeatSetListener{
        /**
         * This is a function, used to pass data on submission of the dialog.
         * @param d This refers to how often the task is to be repeated. This is null if there is no
         *          repeat to be set
         */
        public void onRepeatSet(Integer d);
        //For this function, some thigns need to be taken into account, what about monthly
        // not all months are the same length

    }




}
