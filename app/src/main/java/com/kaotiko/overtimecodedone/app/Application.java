package com.kaotiko.overtimecodedone.app;


import android.graphics.Typeface;

import com.kaotiko.overtimecodedone.utils.common.font.AppFont;

public class Application extends android.app.Application {

    Typeface typeface;

    public void onCreate() {

        super.onCreate();

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFontStencilLight(this);

    }

    public Typeface getTypeface() {

        return typeface;

    }

}
