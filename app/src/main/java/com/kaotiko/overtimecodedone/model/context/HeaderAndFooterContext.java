package com.kaotiko.overtimecodedone.model.context;

import android.content.Context;

import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooter;

import java.util.ArrayList;

public class HeaderAndFooterContext {

    private HeaderAndFooterLocal headerAndFooterLocal;
    public static  final String name = "database";
    private final int databaseVersion = 1;
    private DatabaseHelper databaseHelper;

    public static HeaderAndFooterContext context(Context context, HeaderAndFooterContext headerAndFooterContext) {

        if(headerAndFooterContext == null) {

            headerAndFooterContext = new HeaderAndFooterContext(context);

        }

        return headerAndFooterContext;

    }

    public HeaderAndFooterContext(Context context) {

        databaseHelper = new DatabaseHelper(context, name, null, databaseVersion);

        open();

        headerAndFooterLocal = new HeaderAndFooterLocal(databaseHelper.getDatabase());

    }


    public void open() {

        databaseHelper.open();

    }

    public ArrayList<HeaderAndFooter> getAllHeaders() {

        return headerAndFooterLocal.getAllHeaders();

    }

    public ArrayList<HeaderAndFooter> getAllFooters() {

        return headerAndFooterLocal.getAllFooters();

    }


    public long insert(HeaderAndFooter headerAndFooter) {

        return  headerAndFooterLocal.insert(headerAndFooter);

    }

    public void update(HeaderAndFooter headerAndFooter) {

        headerAndFooterLocal.update(headerAndFooter);

    }

    public void delete(HeaderAndFooter headerAndFooter) {

        headerAndFooterLocal.delete(headerAndFooter);

    }

}
