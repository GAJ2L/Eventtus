package com.gaj2l.eventtus.ioc.components;

import com.gaj2l.eventtus.ioc.modules.AppModule;
import com.gaj2l.eventtus.ioc.modules.RepositoryModule;
import com.gaj2l.eventtus.ioc.modules.ServiceModule;
import com.gaj2l.eventtus.ioc.modules.SessionModule;
import com.gaj2l.eventtus.services.UserService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Component(modules = {ServiceModule.class, RepositoryModule.class, SessionModule.class, AppModule.class})
@Singleton
public interface ServiceComponent {

    UserService getUserService();
}
