package com.gaj2l.eventtus.ioc.modules;

import com.gaj2l.eventtus.busines.validations.UserValidation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Module
public class ValidationModule {
    @Singleton
    @Provides
    public UserValidation providesUserService(UserValidation userValidation) {
        return new UserValidation();
    }
}
