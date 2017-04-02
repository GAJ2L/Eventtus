package com.gaj2l.eventtus.ioc.modules;

import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.repositories.ActivityRepository;
import com.gaj2l.eventtus.repositories.AttachmentRepository;
import com.gaj2l.eventtus.repositories.EvaluationRepository;
import com.gaj2l.eventtus.repositories.EventRepository;
import com.gaj2l.eventtus.repositories.MessageRepository;
import com.gaj2l.eventtus.repositories.PreferenceUserRepository;
import com.gaj2l.eventtus.repositories.TypeAttachmentRepository;
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
    public ActivityRepository providesActivityRepository(SQLiteDatabase sqLiteDatabase) {
        return new ActivityRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public AttachmentRepository providesAttachmentRepository(SQLiteDatabase sqLiteDatabase) {
        return new AttachmentRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public EvaluationRepository providesEvaluationRepository(SQLiteDatabase sqLiteDatabase) {
        return new EvaluationRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public EventRepository providesEventRepository(SQLiteDatabase sqLiteDatabase) {
        return new EventRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public MessageRepository providesMessageRepository(SQLiteDatabase sqLiteDatabase) {
        return new MessageRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public PreferenceUserRepository providesPreferenceUserRepository(SQLiteDatabase sqLiteDatabase) {
        return new PreferenceUserRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public TypeAttachmentRepository providesTypeAttachmentRepository(SQLiteDatabase sqLiteDatabase) {
        return new TypeAttachmentRepository(sqLiteDatabase);
    }

    @Singleton
    @Provides
    public UserRepository providesUserRepository(SQLiteDatabase sqLiteDatabase) {
        return new UserRepository(sqLiteDatabase);
    }
}
