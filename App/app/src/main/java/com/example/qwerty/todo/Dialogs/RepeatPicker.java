package com.example.qwerty.todo.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Qwerty on 3/12/2017.
 */

public class RepeatPicker extends DialogFragment implements RepeatDialog.OnRepeatSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new RepeatDialog(getActivity());
    }

    @Override
    public void onRepeatSet(Integer d) {

    }
}
