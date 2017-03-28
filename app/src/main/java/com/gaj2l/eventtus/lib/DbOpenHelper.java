package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private final static String DATABASE = "eventtus";
    private final static int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 'user' ( '_id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT NOT NULL, 'mail' TEXT NOT NULL, 'method_autentication' TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
