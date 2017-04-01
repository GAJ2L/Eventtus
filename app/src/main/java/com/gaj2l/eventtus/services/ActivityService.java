package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Activity;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class ActivityService extends Service<Activity> {

    public ActivityService(Repository<Activity> repository, Validation<Activity> validation) {
        super(repository, validation);
    }
}