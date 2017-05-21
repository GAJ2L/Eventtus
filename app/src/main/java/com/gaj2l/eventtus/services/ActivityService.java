package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class ActivityService extends Service<Activity> {

    public ActivityService(Repository<Activity> repository, Validation<Activity> validation) {
        super(repository, validation);
    }

    public List<Activity> getActivitiesByEvent(long eventId) {
        String filters[][] = {{"event_id", String.valueOf(eventId)}};
        List<Activity> activities = this.list(filters);
        return (!activities.isEmpty()) ? activities : null;
    }

    public Activity getActivityByServiceId(long serviceId)
    {
        String filters[][] = {{"activity_service_id", String.valueOf(serviceId)}};
        List<Activity> activities = this.list(filters);
        return (!activities.isEmpty()) ? activities.get(0) : null;
    }
}