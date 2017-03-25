package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.Message;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */
public class MessageValidation extends Validation<Message> {

    @Override
    public void validate(Message message) throws Exception{

        if(message.getContent() == null || message.getContent().equals("")){
            throw new Exception();
        }

        if(message.getDt_store() == null ){
            throw new Exception();
        }

        if(message.getUser() == null ){
            throw new Exception();
        }

        if(message.getActivity() == null ){
            throw new Exception();
        }
    }
}

