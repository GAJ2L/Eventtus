package com.gaj2l.eventtus.ioc.components;

import com.gaj2l.eventtus.ioc.modules.AppModule;
import com.gaj2l.eventtus.ioc.modules.RepositoryModule;
import com.gaj2l.eventtus.ioc.modules.ServiceModule;
import com.gaj2l.eventtus.ioc.modules.SessionModule;
import com.gaj2l.eventtus.ioc.modules.ValidationModule;
import com.gaj2l.eventtus.services.ActivityService;
import com.gaj2l.eventtus.services.AttachmentService;
import com.gaj2l.eventtus.services.EvaluationService;
import com.gaj2l.eventtus.services.EventService;
import com.gaj2l.eventtus.services.MessageService;
import com.gaj2l.eventtus.services.PreferenceUserService;
import com.gaj2l.eventtus.services.TypeAttachmentService;
import com.gaj2l.eventtus.services.UserService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

@Component(modules = {ServiceModule.class, RepositoryModule.class, ValidationModule.class, SessionModule.class, AppModule.class})
@Singleton
public interface ServiceComponent {

    ActivityService getActivityService();

    AttachmentService getAttachmentService();

    EvaluationService getEvaluationService();

    EventService getEventService();

    MessageService getMessageService();

    PreferenceUserService getPreferenceUserService();

    TypeAttachmentService getTypeAttachmentService();

    UserService getUserService();
}
