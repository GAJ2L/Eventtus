package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Evaluation;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class EvaluationValidation extends Validation<Evaluation> {

    @Override
    public void validate(Evaluation evaluation) throws Exception{

        if(evaluation.getStars() == 0 ){
            throw new Exception();
        }

        if(evaluation.getDtStore() == null ){
            throw new Exception();
        }
    }
}