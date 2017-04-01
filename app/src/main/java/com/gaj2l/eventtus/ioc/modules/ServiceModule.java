package com.gaj2l.eventtus.ioc.modules;

import com.gaj2l.eventtus.repositories.UserRepository;
import com.gaj2l.eventtus.services.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Module
public class ServiceModule {
    @Singleton
    @Provides
    public UserService providesUserService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
