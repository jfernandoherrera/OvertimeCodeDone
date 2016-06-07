package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private final String recordsCreate = "CREATE TABLE records (id integer primary key autoincrement, date integer, commitId text, description text, durationHours integer, durationMinutes integer);";
    private final String emailsCreate = "CREATE TABLE emails (id integer primary key autoincrement, email text);";
    private final String DATABASE_UPGRADE = "DROP TABLE IF EXISTS records";
    private SQLiteDatabase database;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }

    public SQLiteDatabase getDatabase() {

        return database;

    }

    public void open() {

        database = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(recordsCreate);
        db.execSQL(emailsCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DATABASE_UPGRADE);

    }

}
