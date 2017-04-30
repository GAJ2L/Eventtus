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

public class Preload
{

    private static Dialog preload;

    public static void show(Context context)
    {
        preload = new Dialog(context);
        preload.requestWindowFeature(Window.FEATURE_NO_TITLE);
        preload.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
        preload.setContentView(LayoutInflater.from(context).inflate(R.layout.preloader, null));
        preload.show();
    }

    public static void hide()
    {
        preload.dismiss();
    }
}
