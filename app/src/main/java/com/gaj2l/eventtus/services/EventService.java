package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.models.Event;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class EventService extends Service<Event> {

    public EventService(Repository<Event> repository) {
        super(repository);
    }
}