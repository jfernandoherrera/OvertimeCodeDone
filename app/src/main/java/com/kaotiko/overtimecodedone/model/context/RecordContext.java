package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;

public class RecordContext {

    private RecordLocal recordLocal;
    public static  final String name = "database";
    private final int databaseVersion = 1;
    private DatabaseHelper databaseHelper;

    public RecordContext(Context context) {

        databaseHelper = new DatabaseHelper(context, name, null, databaseVersion);

        open();

        recordLocal = new RecordLocal(databaseHelper.getDatabase());

    }

    public void open() {

     databaseHelper.open();

    }

    public ArrayList<Record> getAllRecords() {

        return recordLocal.getAllRecords();

    }

    public long insertRecord(Record record) {

      return recordLocal.insertRecord(record);

    }

    public void updateRecord(Record record) {

        recordLocal.updateRecord(record);

    }

}
