package com.gaj2l.eventtus.ioc.components;

import android.content.Context;

import com.gaj2l.eventtus.ioc.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    Context getContext();
}
