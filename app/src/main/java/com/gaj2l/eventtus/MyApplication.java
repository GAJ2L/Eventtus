package com.gaj2l.eventtus;

import android.app.Application;

import com.gaj2l.eventtus.ioc.ComponentProvider;

/**
 * Created by Lucas Tomasi
 */

public class MyApplication extends Application {

    public static String EMAIL_APPLICATION = "contact@eventtus.com";

    @Override
    public void onCreate() {
        super.onCreate();

        ComponentProvider.initialize(this);
    }
}