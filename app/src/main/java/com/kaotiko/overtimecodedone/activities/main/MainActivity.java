package com.kaotiko.overtimecodedone.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.adapters.RecordsAdapter;
import com.kaotiko.overtimecodedone.activities.main.fragments.DatePickerFragment;
import com.kaotiko.overtimecodedone.activities.main.fragments.SetupEmailsFragment;
import com.kaotiko.overtimecodedone.model.context.RecordContext;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements DatePickerFragment.OnDateSelected{

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Record> records;
    RecordContext recordContext;

    public static void goToMain(Context context) {

        Intent intent = new Intent(context, MainActivity.class);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        recordContext = new RecordContext(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recordContext.open();

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        records = recordContext.getAllRecords();

        adapter = new RecordsAdapter(records, this);

        recyclerView.setAdapter(adapter);

        toolbar.setTitle(getString(R.string.overtime_code_done));

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Record record = new Record(0,null,"","",0,0);

                records.add(0,record);

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            showSetupEmailDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void showSetupEmailDialog() {

        SetupEmailsFragment setupEmailsFragment = new SetupEmailsFragment();

        String tag = "editOpeningHours";

        setupEmailsFragment.show(getSupportFragmentManager(), tag);

    }

    @Override
    public void onDateSelected(int year, int monthOfYear, int dayOfMonth, Record record) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, monthOfYear, dayOfMonth);

        setCalendar(calendar, record);

    }

    public void setCalendar(Calendar calendar, Record recordId) {

        for(Record record : records) {

            if(record.equals(recordId)) {

                record.setDate(calendar);

                adapter.notifyDataSetChanged();

                break;

            }

        }

    }

}
