package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.TypeAttachment;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class TypeAttachmentValidation extends Validation<TypeAttachment> {

    @Override
    public void validate(TypeAttachment typeAttachment) throws ValidationException {

        if (typeAttachment.getName() == null || typeAttachment.getName().equals("")) {
            throw new ValidationException(R.string.exception_field_name);
        }
    }
}
