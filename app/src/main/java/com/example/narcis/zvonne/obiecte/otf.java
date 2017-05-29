package com.example.narcis.zvonne.obiecte;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class otf extends android.support.v7.widget.AppCompatTextView {

    public otf(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public otf(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public otf(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"fonts/material.ttf");
        this.setTypeface(customFont);
    }
}