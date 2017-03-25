package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Message;

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

