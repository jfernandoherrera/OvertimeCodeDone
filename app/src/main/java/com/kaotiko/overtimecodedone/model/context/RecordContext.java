package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;

public class RecordContext {

    private RecordLocal recordLocal;
    public static  final String name = "database";
    private final int databaseVersion = 1;


    public RecordContext(Context context) {

        recordLocal = new RecordLocal(context, name, null, databaseVersion);

    }

    public void open() {

     recordLocal.open();

    }

    public ArrayList<Record> getAllRecords() {

        return recordLocal.getAllRecords();

    }

    public void insertRecord(Record record) {

        recordLocal.insertRecord(record);

    }

    public void updateRecord(Record record) {

        recordLocal.updateRecord(record);

    }

}
