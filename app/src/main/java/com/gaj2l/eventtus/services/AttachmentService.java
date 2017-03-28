package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.models.Attachment;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class AttachmentService extends Service<Attachment> {

    public AttachmentService(Repository<Attachment> repository) {
        super(repository);
    }
}