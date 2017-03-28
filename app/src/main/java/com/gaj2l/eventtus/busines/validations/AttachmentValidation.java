package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Attachment;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class AttachmentValidation extends Validation<Attachment> {

    @Override
    public void validate(Attachment attachment) throws ValidationException {

        if (attachment.getName() == null || attachment.getName().equals("")) {
            throw new ValidationException(R.string.exception_field_name);
        }

        if (attachment.getLocal() == null || attachment.getName().equals("")) {
            throw new ValidationException(R.string.exception_field_local);
        }

        if (attachment.getActivityId() == 0) {
            throw new ValidationException(R.string.exception_field_activity);
        }

        if (attachment.getTypeAttachmentId() == 0) {
            throw new ValidationException(R.string.exception_field_type_attachment);
        }
    }
}
