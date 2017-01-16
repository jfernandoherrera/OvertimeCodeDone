package com.kaotiko.overtimecodedone.model.context;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.EmailAttributes;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooter;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooterAttributes;
import com.kaotiko.overtimecodedone.model.domain.Record;
import com.kaotiko.overtimecodedone.model.domain.RecordAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class HeaderAndFooterLocal {


    private final String tableName = "headersFooters";
    private SQLiteDatabase database;

    public HeaderAndFooterLocal(SQLiteDatabase database) {

        this.database = database;

    }

    public ArrayList<HeaderAndFooter> getAllHeaders() {

        ArrayList<HeaderAndFooter> headerAndFooters = new ArrayList<>();

        Cursor cursor = database.query(tableName, null, "type = ?", new String[] {HeaderAndFooterAttributes.typeHeader}, null, null, null );

        if(cursor != null && cursor.moveToFirst()) {

            do {

                HeaderAndFooter headerAndFooter = new HeaderAndFooter(cursor.getInt(HeaderAndFooterAttributes.idIndex),

                        cursor.getString(HeaderAndFooterAttributes.textIndex), cursor.getString(HeaderAndFooterAttributes.typeIndex));

                headerAndFooters.add(headerAndFooter);

            } while (cursor.moveToNext());

        }

        Collections.reverse(headerAndFooters);

        cursor.close();

        return headerAndFooters;

    }

    public ArrayList<HeaderAndFooter> getAllFooters() {

        ArrayList<HeaderAndFooter> headerAndFooters = new ArrayList<>();

        Cursor cursor = database.query(tableName, null, "type = ?", new String[] {HeaderAndFooterAttributes.typeFooter}, null, null, null );

        if(cursor != null && cursor.moveToFirst()) {

            do {

                HeaderAndFooter headerAndFooter = new HeaderAndFooter(cursor.getInt(HeaderAndFooterAttributes.idIndex),

                        cursor.getString(HeaderAndFooterAttributes.textIndex), cursor.getString(HeaderAndFooterAttributes.typeIndex));

                headerAndFooters.add(headerAndFooter);

            } while (cursor.moveToNext());

        }

        Collections.reverse(headerAndFooters);

        cursor.close();

        return headerAndFooters;

    }

    public long insert(HeaderAndFooter headerAndFooter) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(HeaderAndFooterAttributes.text, headerAndFooter.getText ());

        contentValues.put(HeaderAndFooterAttributes.type, headerAndFooter.getType());

        return database.insert(tableName, null, contentValues);

    }

    public void update(HeaderAndFooter headerAndFooter) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(HeaderAndFooterAttributes.text, headerAndFooter.getText());

        database.update(tableName, contentValues,"rowid="+ headerAndFooter.getId(), null);

    }

    public void delete(HeaderAndFooter headerAndFooter) {

        database.delete(tableName, "rowid=" + headerAndFooter.getId(), null);

    }

}
