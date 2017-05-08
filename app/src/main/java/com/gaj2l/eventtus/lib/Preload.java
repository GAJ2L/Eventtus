package com.gaj2l.eventtus.lib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import com.gaj2l.eventtus.R;

/**
 * Created by lucas on 30/04/17.
 */

public class Preload extends Dialog
{
    public Preload(Context context)
    {
        super(context);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
        setContentView(LayoutInflater.from(context).inflate(R.layout.preloader, null));
    }
}
