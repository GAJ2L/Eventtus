package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.PreferenceUser;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class PreferenceUserService extends Service<PreferenceUser> {

    public PreferenceUserService(Repository<PreferenceUser> repository, Validation<PreferenceUser> validation) {
        super(repository, validation);
    }
}