package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.Attachment;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class AttachmentService extends Service<Attachment> {

    public AttachmentService(Repository<Attachment> repository, Validation<Attachment> validation) {
        super(repository, validation);
    }

    public List<Attachment> getAttachmentsByActivity(long activityId) {
        String filters[][] = {{"activity_id", String.valueOf(activityId)}};
        List<Attachment> attachments = this.list(filters);
        return (!attachments.isEmpty()) ? attachments : null;
    }
}