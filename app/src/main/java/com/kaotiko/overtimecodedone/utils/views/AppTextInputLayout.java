package com.kaotiko.overtimecodedone.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.kaotiko.overtimecodedone.app.Application;

public class AppTextInputLayout extends TextInputLayout {

    public AppTextInputLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        Typeface typeface = ((Application)context.getApplicationContext()).getTypeface();

        setTypeface(typeface);

    }

}
