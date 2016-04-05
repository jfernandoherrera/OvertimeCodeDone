package com.kaotiko.overtimecodedone.utils.common.font;

import android.content.Context;
import android.graphics.Typeface;

public class AppFont {


    public Typeface getAppFontStencilLight(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Existence-StencilLight.otf");

        return typeface;

    }

}