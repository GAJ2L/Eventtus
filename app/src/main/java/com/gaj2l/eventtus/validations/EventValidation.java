package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Event;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class EventValidation extends Validation<Event> {

    @Override
    public void validate(Event event) throws Exception{

        if(event.getName() == null || event.getName().equals("")){
            throw new Exception();
        }

        if(event.getDescription() == null || event.getDescription().equals("")){
            throw new Exception();
        }

        if(event.getDtStart() == null ){
            throw new Exception();
        }

        if(event.getDtEnd() == null ){
            throw new Exception();
        }

        if(event.getContactName() == null || event.getContactName().equals("")){
            throw new Exception();
        }

        if(event.getContactPhone() == null || event.getContactPhone().equals("")){
            throw new Exception();
        }

        if(event.getContactMail() == null || event.getContactMail().equals("")){
            throw new Exception();
        }
    }
}
