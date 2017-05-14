package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.gaj2l.eventtus.R;

/**
 * Created by lucas.tomasi on 06/05/17.
 */

public  abstract class Message
{
    public static void confirm(Context context, String title, String msg, final Action action)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                action.onPositiveButton();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                action.onNegativeButton();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static abstract class Action
    {
        public abstract void onPositiveButton();
        public abstract void onNegativeButton();
    }
}
