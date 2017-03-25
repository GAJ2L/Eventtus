package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.Event;

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

        if(event.getDt_start() == null ){
            throw new Exception();
        }

        if(event.getDt_end() == null ){
            throw new Exception();
        }

        if(event.getContact_name() == null || event.getContact_name().equals("")){
            throw new Exception();
        }

        if(event.getContact_phone() == null || event.getContact_phone().equals("")){
            throw new Exception();
        }

        if(event.getContact_mail() == null || event.getContact_mail().equals("")){
            throw new Exception();
        }
    }
}
