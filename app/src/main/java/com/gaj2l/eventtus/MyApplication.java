package com.gaj2l.eventtus;

import android.app.Application;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.DbOpenHelper;

/**
 * Created by Lucas Tomasi
 */

public class MyApplication extends Application {

    public static String EMAIL_APPLICATION = "contato.eventtus@gmail.com";
    public static String EMAIL_NAME = "Eventtus";

    @Override
    public void onCreate() {
        super.onCreate();
        DbOpenHelper.deleteDatabase(getApplicationContext());
        ComponentProvider.initialize(this);
    }
}