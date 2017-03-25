package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.PreferenceUser;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class PreferenceUserValidation extends Validation<PreferenceUser> {

    @Override
    public void validate(PreferenceUser preferenceUser) throws Exception{

        if(preferenceUser.getUser() == null ){
            throw new Exception();
        }
    }
}
