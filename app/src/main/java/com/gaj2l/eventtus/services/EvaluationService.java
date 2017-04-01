package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Evaluation;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class EvaluationService extends Service<Evaluation> {

    public EvaluationService(Repository<Evaluation> repository, Validation<Evaluation> validation) {
        super(repository, validation);
    }
}