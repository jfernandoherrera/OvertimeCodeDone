package com.kaotiko.overtimecodedone.model.context;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.EmailAttributes;
import com.kaotiko.overtimecodedone.model.domain.RecordAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class EmailLocal extends SQLiteOpenHelper {

    private final String tableName = "emails";
    private final String DATABASE_CREATE = "CREATE TABLE emails (id integer primary key autoincrement, email text);";
    private final String DATABASE_UPGRADE = "DROP TABLE IF EXISTS emails";
    private SQLiteDatabase database;

    public EmailLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

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

}
