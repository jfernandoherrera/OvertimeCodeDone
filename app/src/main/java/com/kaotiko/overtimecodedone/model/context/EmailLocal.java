package com.kaotiko.overtimecodedone.model.context;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.EmailAttributes;
import com.kaotiko.overtimecodedone.model.domain.RecordAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class EmailLocal  {

    private final String tableName = "emails";
    private SQLiteDatabase database;

    public EmailLocal(SQLiteDatabase database) {

        this.database = database;

    }

    public ArrayList<Email> getAllEmails() {

        ArrayList<Email> emails = new ArrayList<>();

        Cursor cursor = database.query(tableName, null, null, null, null, null, null );

        if(cursor != null && cursor.moveToFirst()) {

            do {

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(cursor.getLong(RecordAttributes.dateIndex));

                Email email = new Email(cursor.getInt(RecordAttributes.idIndex),
                         cursor.getString(EmailAttributes.emailIndex));

                emails.add(email);

            } while (cursor.moveToNext());

        }

        Collections.reverse(emails);

        cursor.close();

        return emails;

    }

    public long insertEmail(Email email) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(EmailAttributes.email, email.getEmail());

        return database.insert(tableName, null, contentValues);

    }

    public void updateEmail(Email email) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(EmailAttributes.email, email.getEmail());

        database.update(tableName, contentValues,"rowid="+ email.getId(), null);

    }

    public void deleteEmail(Email email) {

        database.delete(tableName, "rowid=" + email.getId(), null);

    }

}
