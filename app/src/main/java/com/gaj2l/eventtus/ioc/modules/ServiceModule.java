package com.gaj2l.eventtus.ioc.modules;

import com.gaj2l.eventtus.busines.validations.ActivityValidation;
import com.gaj2l.eventtus.busines.validations.AttachmentValidation;
import com.gaj2l.eventtus.busines.validations.EvaluationValidation;
import com.gaj2l.eventtus.busines.validations.EventValidation;
import com.gaj2l.eventtus.busines.validations.MessageValidation;
import com.gaj2l.eventtus.busines.validations.PreferenceUserValidation;
import com.gaj2l.eventtus.busines.validations.UserValidation;
import com.gaj2l.eventtus.repositories.ActivityRepository;
import com.gaj2l.eventtus.repositories.AttachmentRepository;
import com.gaj2l.eventtus.repositories.EvaluationRepository;
import com.gaj2l.eventtus.repositories.EventRepository;
import com.gaj2l.eventtus.repositories.MessageRepository;
import com.gaj2l.eventtus.repositories.PreferenceUserRepository;
import com.gaj2l.eventtus.repositories.UserRepository;
import com.gaj2l.eventtus.services.ActivityService;
import com.gaj2l.eventtus.services.AttachmentService;
import com.gaj2l.eventtus.services.EvaluationService;
import com.gaj2l.eventtus.services.EventService;
import com.gaj2l.eventtus.services.MessageService;
import com.gaj2l.eventtus.services.PreferenceUserService;
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
    public ActivityService providesActivityService(ActivityRepository activityRepository, ActivityValidation activityValidation) {
        return new ActivityService(activityRepository, activityValidation);
    }

    @Singleton
    @Provides
    public AttachmentService providesAttachmentService(AttachmentRepository attachmentRepository, AttachmentValidation attachmentValidation) {
        return new AttachmentService(attachmentRepository, attachmentValidation);
    }

    @Singleton
    @Provides
    public EvaluationService providesEvaluationService(EvaluationRepository evaluationRepository, EvaluationValidation evaluationValidation) {
        return new EvaluationService(evaluationRepository, evaluationValidation);
    }

    @Singleton
    @Provides
    public EventService providesEventService(EventRepository eventRepository, EventValidation eventValidation) {
        return new EventService(eventRepository, eventValidation);
    }

    @Singleton
    @Provides
    public MessageService providesMessageService(MessageRepository messageRepository, MessageValidation messageValidation) {
        return new MessageService(messageRepository, messageValidation);
    }

    @Singleton
    @Provides
    public PreferenceUserService providesPreferenceUserService(PreferenceUserRepository preferenceUserRepository, PreferenceUserValidation preferenceUserValidation) {
        return new PreferenceUserService(preferenceUserRepository, preferenceUserValidation);
    }

    @Singleton
    @Provides
    public UserService providesUserService(UserRepository userRepository, UserValidation userValidation) {
        return new UserService(userRepository, userValidation);
    }
}
