package com.kaotiko.overtimecodedone.activities.main.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TextView;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private OnDateSelected listener;
    Calendar calendar;
    private TextView textView;
    private Record record;

    public interface OnDateSelected{

        void onDateSelected(int year, int monthOfYear, int dayOfMonth, Record record);
    }

    @Override
    public void onAttach(Activity activity) {

        listener = (OnDateSelected) activity;

        super.onAttach(activity);

    }

    public void setTextView( Record record) {

        this.record = record;


    }

    public void setCalendar(Calendar calendar) {

        this.calendar = calendar;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialogFragment = new DatePickerDialog(getActivity(), this, year, month, day);

        final DialogInterface.OnClickListener  listeners = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        };

        dialogFragment.setButton(DialogInterface.BUTTON_POSITIVE,getContext().getString(R.string.ok), listeners);

        dialogFragment.getDatePicker().setMaxDate(calendar.getTime().getTime());

        return dialogFragment;

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        listener.onDateSelected(year, monthOfYear, dayOfMonth, record);

    }

}
