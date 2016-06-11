package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private final String recordsCreate = "CREATE TABLE records (id integer primary key autoincrement, date integer, commitId text, description text, durationHours integer, durationMinutes integer);";
    private final String emailsCreate = "CREATE TABLE emails (id integer primary key autoincrement, email text);";
    private final String headersFootersCreate = "CREATE TABLE headersFooters (id integer primary key autoincrement, text text, type text);";
    private final String databaseUpgradeRecords = "DROP TABLE IF EXISTS records";
    private final String databaseUpgradeEmails = "DROP TABLE IF EXISTS emails";
    private final String databaseUpgradeHeadersFooters = "DROP TABLE IF EXISTS headersFooters";
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
        db.execSQL(headersFootersCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(databaseUpgradeEmails);
        db.execSQL(databaseUpgradeHeadersFooters);
        db.execSQL(databaseUpgradeRecords);

    }

}
