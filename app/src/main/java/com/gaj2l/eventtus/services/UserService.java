package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.models.User;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class UserService extends Service<User> {

    public UserService(Repository<User> repository) {
        super(repository);
    }
}