package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Message;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class MessageService extends Service<Message> {

    public MessageService(Repository<Message> repository, Validation<Message> validation) {
        super(repository, validation);
    }
}