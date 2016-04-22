package com.kaotiko.overtimecodedone.activities.main.adapters.viewholders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.MainActivity;
import com.kaotiko.overtimecodedone.activities.main.fragments.DatePickerFragment;
import com.kaotiko.overtimecodedone.model.context.RecordContext;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.Calendar;

public class RecordViewHolder extends RecyclerView.ViewHolder{
    private TextView textDate;
    private EditText commitId;
    private EditText durationHours;
    private EditText durationMinutes;
    private EditText description;
    private RelativeLayout floatContainer;
    private FloatingActionButton floatingActionButton;
    private RecordContext recordContext;
    private Calendar date;
    private Record record;
    private TextWatcher commitIdTextWatcher;

    public void setRecord(Record record) {

        this.record = record;

    }

    public Record getRecord() {

        return record;

    }

    public void setDate(String date) {

      textDate.setText(date);

    }

    public void setCommitId(String commitId) {

        this.commitId.setText(commitId);

    }

    public void setDurationHours(String durationHours) {

        this.durationHours.setText(durationHours);

    }

    public void setDurationMinutes(String durationMinutes) {

        this.durationMinutes.setText(durationMinutes);

    }

    public void setDescription(String description) {

        this.description.setText(description);

    }

    public RecordViewHolder(View itemView) {

        super(itemView);

        recordContext = new RecordContext(itemView.getContext());

        floatContainer = (RelativeLayout) itemView.findViewById(R.id.floatContainer);

        commitId = (EditText) itemView.findViewById(R.id.textCommitId);

        description = (EditText) itemView.findViewById(R.id.description);

        durationHours = (EditText) itemView.findViewById(R.id.numberPickerHour);

        durationMinutes = (EditText) itemView.findViewById(R.id.numberPickerMinutes);

        textDate = (TextView) itemView.findViewById(R.id.textDate);

        floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.fab);

        textDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectDate(v);

            }

        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                recordContext.open();

                updateRecord();

               if(record.getId() == 0) {

                   recordContext.insertRecord(record);

               } else {

                   recordContext.updateRecord(record);
               }

                unsetFloatingActionButton();

            }

        });

        addChangeTextListeners();

    }

    public void setFloatingActionButton() {

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) floatContainer.getLayoutParams();

        WindowManager wm = (WindowManager) commitId.getContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        double size = metrics.scaledDensity;

        params.height = (int) (265 * size);

        floatingActionButton.setVisibility(View.VISIBLE);

        floatContainer.setLayoutParams(params);

        record.setWaitingSave(true);

    }

    public void unsetFloatingActionButton() {

        record.setWaitingSave(false);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) floatContainer.getLayoutParams();

            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;

            floatContainer.setLayoutParams(params);

        floatingActionButton.setVisibility(View.GONE);

    }

    public void addChangeTextListeners() {

        commitIdTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                RecordViewHolder.this.afterTextChanged();

            }

        };

        commitId.addTextChangedListener(commitIdTextWatcher);

        textDate.addTextChangedListener(commitIdTextWatcher);

        durationHours.addTextChangedListener(commitIdTextWatcher);

        durationMinutes.addTextChangedListener(commitIdTextWatcher);

        description.addTextChangedListener(commitIdTextWatcher);

    }

    private void afterTextChanged() {

        if(! record.getIsProgrammaticallyChanged()) {

            updateRecord();

            boolean idIsEmpty = isEmpty(commitId.getText().toString()) && isEmpty(description.getText().toString());

            boolean dateIsEmpty = isEmpty(textDate.getText().toString());

            boolean durationIsEmpty = isEmpty(durationHours.getText().toString()) && isEmpty(durationMinutes.getText().toString());

            if (!idIsEmpty && !dateIsEmpty && !durationIsEmpty) {

                setFloatingActionButton();

            }

        }

    }

    private boolean isEmpty(String string) {

        boolean isEmpty = string.equals("");

        return isEmpty;

    }

    private void updateRecord() {

        int hours =  durationHours.getText().toString().equals("") ? 0 :  Integer.parseInt(durationHours.getText().toString());

        int minutes =  durationMinutes.getText().toString().equals("") ? 0 :  Integer.parseInt(durationMinutes.getText().toString());

        record.setCommitId(commitId.getText().toString());

        record.setDescription(description.getText().toString());

        record.setDurationHours(hours);

        record.setDurationMinutes(minutes);

    }

    private void selectDate(View v) {

        updateRecord();

        record.setOnEditedDate(true);

        DatePickerFragment newFragment = new DatePickerFragment();

        newFragment.setTextView(record);

        String tag = "timePicker";

        date = Calendar.getInstance();

        newFragment.setCalendar(date);

        newFragment.show(((MainActivity)v.getContext()).getSupportFragmentManager(), tag);

    }

}
