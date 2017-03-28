package com.gaj2l.eventtus.busines.validations;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.exceptions.ValidationException;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Evaluation;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class EvaluationValidation extends Validation<Evaluation> {

    @Override
    public void validate(Evaluation evaluation) throws ValidationException {

        if(evaluation.getStars() == 0 ){
            throw new ValidationException(R.string.exception_field_stars);
        }

        if(evaluation.getDtStore() == null ){
            throw new ValidationException(R.string.exception_field_dt_store);
        }
    }
}