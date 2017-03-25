package com.gaj2l.eventtus.validations;

import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.lib.Validation;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class ActivityValidation extends Validation<Activity> {

    @Override
    public void validate(Activity activity) throws Exception{

        if(activity.getName() == null || activity.getName().equals("")){
            throw new Exception();
        }

        if(activity.getDtStart() == null){
            throw new Exception();
        }

        if(activity.getDtEnd() == null){
            throw new Exception();
        }

        if(activity.getLocalName() == null || activity.getName().equals("")){
            throw new Exception();
        }

        if(activity.getEvent() == null){
            throw new Exception();
        }

        if(activity.getEvaluation() == null){
            throw new Exception();
        }
    }
}
