package com.gaj2l.eventtus;

import android.app.Application;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by Jackson Majolo
 */

public class MyApplication extends Application {

    public static String EMAIL_APPLICATION = "contato.eventtus@gmail.com";
    public static String EMAIL_NAME = "Eventtus";

    @Override
    public void onCreate() {
        super.onCreate();

        //FIXME - APAGA BASE
        //DbOpenHelper.deleteDatabase(getApplicationContext());

        AndroidThreeTen.init(this);
        ComponentProvider.initialize(this);
    }
}