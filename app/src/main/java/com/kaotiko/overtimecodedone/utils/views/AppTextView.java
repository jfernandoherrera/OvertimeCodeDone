package com.kaotiko.overtimecodedone.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.kaotiko.overtimecodedone.app.Application;

public class AppTextView extends TextView{


    public AppTextView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        Typeface typeface = ((Application)context.getApplicationContext()).getTypeface();

                setTypeface(typeface);

    }

}