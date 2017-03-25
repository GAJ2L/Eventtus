package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.User;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class UserValidation extends Validation<User> {

    @Override
    public void validate(User user) throws Exception{

        if(user.getName() == null || user.getName().equals("")){
            throw new Exception();
        }

        if(user.getMail() == null || user.getMail().equals("")){
            throw new Exception();
        }

        if(user.getMethodAutentication() == null || user.getMethodAutentication().equals("")){
            throw new Exception();
        }
    }
}