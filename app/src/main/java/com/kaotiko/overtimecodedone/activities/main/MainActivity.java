package com.kaotiko.overtimecodedone.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.adapters.EmailsAdapter;
import com.kaotiko.overtimecodedone.activities.main.adapters.HeaderAndFooterAdapter;
import com.kaotiko.overtimecodedone.activities.main.adapters.RecordsAdapter;
import com.kaotiko.overtimecodedone.activities.main.fragments.DatePickerFragment;
import com.kaotiko.overtimecodedone.activities.main.fragments.SetupEmailsFragment;
import com.kaotiko.overtimecodedone.activities.main.fragments.SetupHeaderAndFooterFragment;
import com.kaotiko.overtimecodedone.model.context.RecordContext;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooter;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooterAttributes;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements DatePickerFragment.OnDateSelected, EmailsAdapter.OnEmailSelected, SetupEmailsFragment.OnEmailsFinished
        ,SetupHeaderAndFooterFragment.OnReadyEmail, HeaderAndFooterAdapter.OnHeaderFooterSelected{

    private ArrayList<Email> emails;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Record> records;
    private HeaderAndFooter header;
    private HeaderAndFooter footer;

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

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings && ! records.isEmpty()) {

            showSetupEmailDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void showSetupEmailDialog() {

        SetupEmailsFragment setupEmailsFragment = new SetupEmailsFragment();

        String tag = "editOpeningHours";

        emails = new ArrayList<>();

        setupEmailsFragment.show(getSupportFragmentManager(), tag);

    }


    public void showSetupHeaderFooterDialog() {

        SetupHeaderAndFooterFragment  setupHeaderAndFooterFragment = new SetupHeaderAndFooterFragment();

        String tag = "HeaderAndFooter";

        setupHeaderAndFooterFragment.show(getSupportFragmentManager(), tag);

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

                record.setOnEditedDate(true);

                adapter.notifyDataSetChanged();

                break;

            }

        }

    }

    @Override
    public boolean onEmailSelected(Email email) {

        boolean isThere = emailIsThere(email);

        if(isThere) {

            emails.remove(email);

        } else {

            emails.add(email);

        }

        return isThere;

    }

    private boolean emailIsThere(Email email) {

        boolean isThere = false;

        for(Email temp : emails) {

            if(temp.equals(email)) {

                isThere = true;

                break;

            }
        }

        return isThere;

    }

    @Override
    public boolean onEmailsFinished() {

        boolean thereEmail = ! emails.isEmpty();

        if(thereEmail) {

            showSetupHeaderFooterDialog();

        }

        return thereEmail;

    }


    @Override
    public boolean onHeaderFooterSelected(HeaderAndFooter headerAndFooter) {

        boolean isThere = false;

        if(headerAndFooter.getType().equals(HeaderAndFooterAttributes.typeHeader)) {

            if(headerAndFooter.equals(header)) {

                isThere = true;

                header = null;

            } else {


                header = headerAndFooter;
            }

        } else {

            if(headerAndFooter.equals(footer)) {

                isThere = true;

                footer = null;

            } else {

                footer = headerAndFooter;

            }

        }

        return isThere;

    }


    private void createEmail() {

        String[] emailsToSend = new String[emails.size()];

        for(int i = 0; i < emails.size(); i++) {

            emailsToSend[i] = emails.get(i).getEmail() ;

        }

        String msg = header.getText() + " ";

        for(Record record : records) {

            String commitID = record.getCommitId() == null || record.getCommitId().equals("") ? "" : getString(R.string.commitId) + " " + record.getCommitId();

            String description = record.getDescription() == null || record.getDescription().equals("") ? "" : getString(R.string.description) + " " +

                    record.getDescription() + " \n";

            msg = msg + "\n" + commitID + " \n" +

            getString(R.string.date) + " " + dateFormat(record.getDate())+ " \n" +

            getString(R.string.duration) + " " + getDuration(record) + "\n" + description;

        }

        msg = msg + "\n" + footer.getText();

        String typeEmail = "message/rfc822";

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType(typeEmail);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra(android.content.Intent.EXTRA_EMAIL, emailsToSend);

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));

        intent.putExtra(Intent.EXTRA_TEXT, msg);

        Intent mailer = Intent.createChooser(intent, null);

        startActivity(mailer);

    }

    @Override
    public void onReadyEmail() {

        createEmail();

    }

    private String getDuration(Record record) {

        String duration = "";

        if(record.getDurationHours() != 0) {

            duration = duration + record.getDurationHours() + "h ";

        }

        if (record.getDurationMinutes() != 0) {

            duration = duration + record.getDurationMinutes() + "m ";

        }

        return duration;

    }

    private String dateFormat(Calendar calendar){

        String title = DateUtils.formatDateTime(this,

                calendar.getTimeInMillis(),

                DateUtils.FORMAT_SHOW_DATE

                        | DateUtils.FORMAT_SHOW_WEEKDAY

                        | DateUtils.FORMAT_SHOW_YEAR

                        | DateUtils.FORMAT_ABBREV_MONTH

                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

        return title;

    }

}
