package com.kaotiko.overtimecodedone.activities.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.adapters.viewholders.RecordViewHolder;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


public class RecordsAdapter extends RecyclerView.Adapter {

    private ArrayList<Record> records;
    private Context context;

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


    private void bindingViewHolder(RecordViewHolder holder, int position) {

        Record record = records.get(position);

        holder.setRecord(record);

        boolean isWaitingSave = record.getIsWaitingSave();

        boolean onEditedDate = record.getOnEditedDate();

        boolean idIsEmpty = isEmpty(record.getCommitId()) && isEmpty(record.getDescription());

        boolean dateIsEmpty = record.getDate() == null;

        boolean durationIsEmpty = record.getDurationHours() == 0 && record.getDurationMinutes() == 0;

        if(onEditedDate && ! idIsEmpty && ! dateIsEmpty && ! durationIsEmpty) {

            record.setOnEditedDate(false);

        }

        if(isWaitingSave) {

            holder.setFloatingActionButton();

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


}
