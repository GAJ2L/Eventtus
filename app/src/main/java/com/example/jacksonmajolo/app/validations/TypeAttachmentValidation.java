package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.TypeAttachment;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class TypeAttachmentValidation extends Validation<TypeAttachment> {

    @Override
    public void validate(TypeAttachment typeAttachment) throws Exception{

        if(typeAttachment.getName() == null || typeAttachment.getName().equals("")){
            throw new Exception();
        }
    }
}
