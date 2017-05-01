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
    private static Preload preload;

    private Preload(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
        setContentView(LayoutInflater.from(context).inflate(R.layout.preloader, null));
    }

    public static Dialog getInstance(Context context)
    {
        if( preload == null )
        {
            preload = new Preload(context);
        }
        else if( !preload.getContext().equals(context) )
        {
            preload = new Preload(context);
        }

        return preload;
    }
}
