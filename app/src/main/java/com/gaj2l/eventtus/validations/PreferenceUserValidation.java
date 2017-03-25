package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.PreferenceUser;

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
