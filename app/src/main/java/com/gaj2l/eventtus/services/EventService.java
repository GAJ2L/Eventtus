package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.repositories.ActivityRepository;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class EventService extends Service<Event> {

    public EventService(Repository<Event> repository, Validation<Event> validation) {
        super(repository, validation);
    }

    public List<Event> getEventsByUser(long userId) {
        String filters[][] = {{"user_id", String.valueOf(userId)}};
        List<Event> events = this.list(filters);
        return (!events.isEmpty()) ? events : null;
    }

    public void clearEvent( Event e ) throws Exception
    {
        String filters[][] = {
                {"user_id", String.valueOf(e.getUserId())},
                {"event_service_id", String.valueOf(e.getEventServiceId())}
        };

        List<Event> events = this.list(filters);

        if( events != null && !events.isEmpty() )
        {
            for (Event event : events)
            {
                List<Activity> activities = ComponentProvider.getServiceComponent().getActivityService().getActivitiesByEvent(event.getId());

                if( activities != null && !activities.isEmpty() )
                {
                    for (Activity activity : activities)
                    {
                        List<Attachment> attachments = ComponentProvider.getServiceComponent().getAttachmentService().getAttachmentsByActivity(activity.getId());

                        if( attachments != null && !attachments.isEmpty() )
                        {
                            for (Attachment attachment : attachments)
                            {
                                ComponentProvider.getServiceComponent().getAttachmentService().delete(attachment);
                            }
                        }

                        ComponentProvider.getServiceComponent().getActivityService().delete(activity);
                    }
                }

                delete(event);
            }
        }
    }
}