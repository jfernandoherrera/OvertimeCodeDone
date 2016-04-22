package com.kaotiko.overtimecodedone.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.kaotiko.overtimecodedone.app.Application;

public class AppEditText extends EditText {

    public AppEditText(Context context, AttributeSet attrs) {

        super(context, attrs);

        Typeface typeface = ((Application)context.getApplicationContext()).getTypeface();

        setTypeface(typeface);

    }

}
