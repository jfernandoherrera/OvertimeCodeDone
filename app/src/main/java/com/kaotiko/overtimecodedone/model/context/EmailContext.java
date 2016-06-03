package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;

import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.Record;

import java.util.ArrayList;

public class EmailContext {

    private EmailLocal emailLocal;
    public static  final String name = "database";
    private final int databaseVersion = 1;

    public EmailContext(Context context) {

        emailLocal = new EmailLocal(context, name, null, databaseVersion);

    }


    public void open() {

        emailLocal.open();

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

}
