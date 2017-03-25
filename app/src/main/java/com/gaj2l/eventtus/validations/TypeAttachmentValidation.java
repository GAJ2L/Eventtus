package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.TypeAttachment;

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
