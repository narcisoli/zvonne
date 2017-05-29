package com.example.narcis.zvonne.obiecte;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;



public class butonotf extends android.support.v7.widget.AppCompatButton {

    public butonotf(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public butonotf(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public butonotf(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"fonts/material.ttf");
        this.setTypeface(customFont);
    }
}