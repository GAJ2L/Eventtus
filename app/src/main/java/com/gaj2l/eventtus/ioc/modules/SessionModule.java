package com.gaj2l.eventtus.ioc.modules;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.DbOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Module
public class SessionModule {

    @Singleton
    @Provides
    public SQLiteDatabase providesSqLiteDatabase(Context context) {
        return new DbOpenHelper(context).getWritableDatabase();
    }
}
