package com.gaj2l.eventtus.ioc.modules;

import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.repositories.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public UserRepository providesUserRepository(SQLiteDatabase sqLiteDatabase) {
        return new UserRepository(sqLiteDatabase);
    }
}
