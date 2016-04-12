package com.kaotiko.overtimecodedone.model.context;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kaotiko.overtimecodedone.model.domain.Record;
import com.kaotiko.overtimecodedone.model.domain.RecordAttributes;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordLocal extends SQLiteOpenHelper {

    private final String tableName = "records";
    private final String DATABASE_CREATE = "CREATE TABLE " + tableName + " (id integer primary key autoincrement, date integer, commitId text, description text, durationHours integer, durationMinutes integer);";
    private final String DATABASE_UPGRADE = "DROP TABLE IF EXISTS records";
    private SQLiteDatabase database;

    public RecordLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }

    public void open() {

        database = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DATABASE_UPGRADE);

    }


    public ArrayList<Record> getAllRecords() {

        ArrayList<Record> records = new ArrayList<>();

        Cursor cursor = database.query(tableName, null, null, null, null, null, null );

        if(cursor != null && cursor.moveToFirst()) {

            do {

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(cursor.getLong(RecordAttributes.dateIndex));

                Record record = new Record(cursor.getInt(RecordAttributes.idIndex),
                        calendar, cursor.getString(RecordAttributes.commitIdIndex),
                        cursor.getString(RecordAttributes.descriptionIndex),
                        cursor.getInt(RecordAttributes.durationHoursIndex),
                        cursor.getInt(RecordAttributes.durationMinutesIndex));

               records.add(record);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return records;

    }

    public void insertRecord(Record record) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(RecordAttributes.commitId, record.getCommitId());

        contentValues.put(RecordAttributes.date, record.getDate().getTimeInMillis());

        contentValues.put(RecordAttributes.description, record.getDescription());

        contentValues.put(RecordAttributes.durationHours, record.getDurationHours());

        contentValues.put(RecordAttributes.durationMinutes, record.getDurationMinutes());

        database.insert(tableName, null, contentValues);

    }

    public void updateRecord(Record record) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(RecordAttributes.commitId, record.getCommitId());

        contentValues.put(RecordAttributes.date, record.getDate().getTimeInMillis());

        contentValues.put(RecordAttributes.description, record.getDescription());

        contentValues.put(RecordAttributes.durationHours, record.getDurationHours());

        contentValues.put(RecordAttributes.durationMinutes, record.getDurationMinutes());

        database.update(tableName, contentValues,"rowid="+ record.getId(), null);

    }

}
