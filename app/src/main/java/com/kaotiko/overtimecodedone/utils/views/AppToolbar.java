package com.kaotiko.overtimecodedone.utils.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.utils.common.font.AppFont;

public class AppToolbar extends Toolbar {

    private TextView textView;

    public AppToolbar(Context context, AttributeSet attrs) {

        super(context, attrs);

        super.setTitle("");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.toolbar, this);

        textView = (TextView) findViewById(R.id.toolbarTextView);

        AppFont appFont = new AppFont();

        textView.setTypeface(appFont.getAppFontCaptureIt(context));

    }

    public AppToolbar(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    public void setTitle(int resId) {

        textView.setText(resId);

    }

    @Override
    public void setTitle(CharSequence text) {

        textView.setText(text);

    }

}