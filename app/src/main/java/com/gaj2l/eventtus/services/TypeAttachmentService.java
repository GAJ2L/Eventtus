package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.models.TypeAttachment;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class TypeAttachmentService extends Service<TypeAttachment> {

    public TypeAttachmentService(Repository<TypeAttachment> repository) {
        super(repository);
    }
}