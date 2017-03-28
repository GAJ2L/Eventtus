package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.User;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class UserValidation extends Validation<User> {

    @Override
    public void validate(User user) throws ValidationException {

        if(user.getName() == null || user.getName().equals("")){
            throw new ValidationException(R.string.exception_field_name);
        }

        if(user.getMail() == null || user.getMail().equals("")){
            throw new ValidationException(R.string.exception_field_mail);
        }

        if(user.getMethodAutentication() == null || user.getMethodAutentication().equals("")){
            throw new ValidationException(R.string.exception_field_method_autentication);
        }
    }
}