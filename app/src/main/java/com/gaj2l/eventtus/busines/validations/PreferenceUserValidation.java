package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.PreferenceUser;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class PreferenceUserValidation extends Validation<PreferenceUser> {

    @Override
    public void validate(PreferenceUser preferenceUser) throws ValidationException {

        if (preferenceUser.getUserId() == 0) {
            throw new ValidationException(R.string.exception_field_user);
        }
    }
}
