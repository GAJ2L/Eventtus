package com.example.jacksonmajolo.app.validations;

import com.example.jacksonmajolo.app.lib.Validation;
import com.example.jacksonmajolo.app.models.Evaluation;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class EvaluationValidation extends Validation<Evaluation> {

    @Override
    public void validate(Evaluation evaluation) throws Exception{

        if(evaluation.getStars() == 0 ){
            throw new Exception();
        }

        if(evaluation.getDt_store() == null ){
            throw new Exception();
        }
    }
}