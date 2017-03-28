package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Event;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class EventValidation extends Validation<Event> {

    @Override
    public void validate(Event event) throws ValidationException {

        if(event.getName() == null || event.getName().equals("")){
            throw new ValidationException(R.string.exception_field_name);
        }

        if(event.getDescription() == null || event.getDescription().equals("")){
            throw new ValidationException(R.string.exception_field_description);
        }

        if(event.getDtStart() == null ){
            throw new ValidationException(R.string.exception_field_dt_start);
        }

        if(event.getDtEnd() == null ){
            throw new ValidationException(R.string.exception_field_dt_end);
        }

        if(event.getContactName() == null || event.getContactName().equals("")){
            throw new ValidationException(R.string.exception_field_contact_name);
        }

        if(event.getContactPhone() == null || event.getContactPhone().equals("")){
            throw new ValidationException(R.string.exception_field_contact_phone);
        }

        if(event.getContactMail() == null || event.getContactMail().equals("")){
            throw new ValidationException(R.string.exception_field_contact_mail);
        }
    }
}
