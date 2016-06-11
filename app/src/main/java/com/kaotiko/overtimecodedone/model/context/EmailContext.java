package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;

import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;

public class EmailContext {

    private EmailLocal emailLocal;
    public static  final String name = "database";
    private final int databaseVersion = 1;
    private DatabaseHelper databaseHelper;

    public EmailContext(Context context) {

        databaseHelper = new DatabaseHelper(context, name, null, databaseVersion);

        open();

        emailLocal = new EmailLocal(databaseHelper.getDatabase());

    }

    public void open() {

        databaseHelper.open();

    }

    public ArrayList<Email> getAllEmails() {

        return emailLocal.getAllEmails();

    }

    public long insertEmail(Email email) {

        return emailLocal.insertEmail(email);

    }

    public void updateEmail(Email email) {

        emailLocal.updateEmail(email);

    }

    public void deleteEmail(Email email) {

        emailLocal.deleteEmail(email);

    }

}
