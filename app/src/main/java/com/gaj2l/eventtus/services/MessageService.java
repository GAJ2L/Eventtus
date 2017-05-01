package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Message;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class MessageService extends Service<Message> {

    public MessageService(Repository<Message> repository, Validation<Message> validation) {
        super(repository, validation);
    }

    public List<Message> getMessagesByActivity(long activityId) {
        String filters[][] = {{"activity_id", String.valueOf(activityId)}};
        List<Message> messages = this.list(filters);
        return (!messages.isEmpty()) ? messages : null;
    }
}