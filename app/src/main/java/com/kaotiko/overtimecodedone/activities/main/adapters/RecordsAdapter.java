package com.kaotiko.overtimecodedone.activities.main.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.MainActivity;
import com.kaotiko.overtimecodedone.activities.main.fragments.DatePickerFragment;
import com.kaotiko.overtimecodedone.model.context.RecordContext;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


public class RecordsAdapter extends RecyclerView.Adapter {

    private ArrayList<Record> records;
    private Context context;
    private RecordContext recordContext;
    private int savedPosition;
    public RecordsAdapter( ArrayList<Record> records, Context context) {

        super();

        this.context = context;
        this.records = records;

    }


    private String dateFormat(Calendar calendar){

        String title = DateUtils.formatDateTime(context,

                calendar.getTimeInMillis(),

                DateUtils.FORMAT_SHOW_DATE

                        | DateUtils.FORMAT_SHOW_WEEKDAY

                        | DateUtils.FORMAT_SHOW_YEAR

                        | DateUtils.FORMAT_ABBREV_MONTH

                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

        return title;

    }

    private boolean isEmpty(String string) {

        boolean isEmpty = string.equals("");

        return isEmpty;

    }


    private void bindingViewHolder(final RecordViewHolder holder, final int position) {

        final Record record = records.get(position);

        holder.removeTextChangedListeners();

        holder.setRecord(position);

        boolean isWaitingSave = record.getIsWaitingSave();

        if(isWaitingSave) {

            holder.setFloatingActionButton();

            savedPosition = position;

        } else {

            holder.unsetFloatingActionButton();

        }

        Calendar calendar = record.getDate();

        if(calendar != null) {

            holder.setDate(dateFormat(record.getDate()));

        } else {

            holder.setDate("");

        }

        record.setIsProgrammaticallyChanged(true);

        holder.setDescription(record.getDescription());

        String commitId = record.getCommitId();

        holder.setCommitId(commitId);

        String  hours = String.valueOf(record.getDurationHours());

        String zero = "0";

        if(! hours.equals(zero)) {

            holder.setDurationHours(hours);

        } else {

            holder.setDurationHours("");

        }

        String minutes = String.valueOf(record.getDurationMinutes());

        if(! minutes.equals(zero)) {

            holder.setDurationMinutes(minutes);

        } else {

            holder.setDurationMinutes("");

        }

        record.setIsProgrammaticallyChanged(false);

        holder.addChangeTextListeners();

        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                recordContext.open();

                holder.updateRecord();

                if(records.get(position).getId() == 0) {

                    long id = recordContext.insertRecord(records.get(position));

                    records.get(position).setId(id);

                } else {

                    recordContext.updateRecord(records.get(position));
                }

                holder.unsetFloatingActionButton();

                notifyDataSetChanged();

            }

        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_view,parent, false);

        RecordViewHolder recordViewHolder = new RecordViewHolder(v);

        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        bindingViewHolder((RecordViewHolder) holder, position);

    }

    @Override
    public int getItemCount() {

        return records.size();

    }


    public class RecordViewHolder extends RecyclerView.ViewHolder{
        private TextView textDate;
        private EditText commitId;
        private EditText durationHours;
        private EditText durationMinutes;
        private EditText description;
        private RelativeLayout floatContainer;
        protected FloatingActionButton floatingActionButton;
        private Calendar date;
        private int record;
        private TextWatcher commitIdTextWatcher;

        public void setRecord(int record) {

            this.record = record;

        }

        public int getRecord() {

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

            records.get(record).setWaitingSave(true);

        }

        public void unsetFloatingActionButton() {

            records.get(record).setWaitingSave(false);

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

        protected void removeTextChangedListeners() {

            commitId.removeTextChangedListener(commitIdTextWatcher);

            textDate.removeTextChangedListener(commitIdTextWatcher);

            durationHours.removeTextChangedListener(commitIdTextWatcher);

            durationMinutes.removeTextChangedListener(commitIdTextWatcher);

            description.removeTextChangedListener(commitIdTextWatcher);
        }

        private void afterTextChanged() {

            if(! records.get(record).getIsProgrammaticallyChanged()) {

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

            records.get(record).setCommitId(commitId.getText().toString());

            records.get(record).setDescription(description.getText().toString());

            records.get(record).setDurationHours(hours);

            records.get(record).setDurationMinutes(minutes);

        }

        private void selectDate(View v) {

            updateRecord();

            records.get(record).setOnEditedDate(true);

            DatePickerFragment newFragment = new DatePickerFragment();

            newFragment.setTextView(records.get(record));

            String tag = "timePicker";

            date = Calendar.getInstance();

            newFragment.setCalendar(date);

            newFragment.show(((MainActivity)v.getContext()).getSupportFragmentManager(), tag);

        }

    }

}
