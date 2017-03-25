package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public abstract class DbOpenHelper extends SQLiteOpenHelper {

    private final static String DATABASE = "eventtus";
    private final static int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }
}
