package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Activity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class ActivityValidation extends Validation<Activity> {

    @Override
    public void validate(Activity activity) throws ValidationException {

        if (activity.getName() == null || activity.getName().equals("")) {
            throw new ValidationException(R.string.exception_field_name);
        }

        if (activity.getDtStart() == null) {
            throw new ValidationException(R.string.exception_field_dt_start);
        }

        if (activity.getDtEnd() == null) {
            new ValidationException(R.string.exception_field_dt_end);
        }

        if (activity.getLocalName() == null || activity.getName().equals("")) {
            new ValidationException(R.string.exception_field_local_name);
        }

        if (activity.getEventId() == 0) {
            new ValidationException(R.string.exception_field_event);
        }

        if (activity.getEvaluationId() == 0) {
            new ValidationException(R.string.exception_field_evaluation);
        }
    }
}
