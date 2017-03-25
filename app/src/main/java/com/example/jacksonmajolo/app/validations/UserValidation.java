package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.User;

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

        if(user.getMethod_autentication() == null || user.getMethod_autentication().equals("")){
            throw new Exception();
        }
    }
}