package com.gaj2l.eventtus.ioc.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context providesContext() {
        return this.application;
    }
}
