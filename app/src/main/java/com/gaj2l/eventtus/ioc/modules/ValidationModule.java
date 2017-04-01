package com.gaj2l.eventtus.ioc.modules;

import com.gaj2l.eventtus.busines.validations.ActivityValidation;
import com.gaj2l.eventtus.busines.validations.AttachmentValidation;
import com.gaj2l.eventtus.busines.validations.EvaluationValidation;
import com.gaj2l.eventtus.busines.validations.EventValidation;
import com.gaj2l.eventtus.busines.validations.MessageValidation;
import com.gaj2l.eventtus.busines.validations.PreferenceUserValidation;
import com.gaj2l.eventtus.busines.validations.TypeAttachmentValidation;
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
    public ActivityValidation providesActivityValidate() {
        return new ActivityValidation();
    }

    @Singleton
    @Provides
    public AttachmentValidation providesAttachmentValidate() {
        return new AttachmentValidation();
    }

    @Singleton
    @Provides
    public EvaluationValidation providesEvaluationValidate() {
        return new EvaluationValidation();
    }

    @Singleton
    @Provides
    public EventValidation providesEventValidate() {
        return new EventValidation();
    }

    @Singleton
    @Provides
    public MessageValidation providesMessageValidate() {
        return new MessageValidation();
    }

    @Singleton
    @Provides
    public PreferenceUserValidation providesPreferenceUserValidate() {
        return new PreferenceUserValidation();
    }

    @Singleton
    @Provides
    public TypeAttachmentValidation providesTypeAttachmentValidate() {
        return new TypeAttachmentValidation();
    }

    @Singleton
    @Provides
    public UserValidation providesUserValidate() {
        return new UserValidation();
    }
}
