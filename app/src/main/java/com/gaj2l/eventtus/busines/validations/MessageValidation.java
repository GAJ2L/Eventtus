package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Message;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */
public class MessageValidation extends Validation<Message> {

    @Override
    public void validate(Message message) throws ValidationException {

        if (message.getContent() == null || message.getContent().equals("")) {
            throw new ValidationException(R.string.exception_field_content);
        }

        if (message.getDtStore() == null) {
            throw new ValidationException(R.string.exception_field_dt_store);
        }

        if (message.getUserId() == 0) {
            throw new ValidationException(R.string.exception_field_user);
        }

        if (message.getActivityId() == 0) {
            throw new ValidationException(R.string.exception_field_activity);
        }
    }
}

