package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Event;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class EventService extends Service<Event> {

    public EventService(Repository<Event> repository, Validation<Event> validation) {
        super(repository, validation);
    }

    public List<Event> getEventsByUser(long userId) {
        String filters[][] = {{"user_id", String.valueOf(userId)}};
        List<Event> events = this.list(filters);
        return (!events.isEmpty()) ? events : null;
    }
}