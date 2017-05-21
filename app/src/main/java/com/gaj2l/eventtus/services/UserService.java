package com.gaj2l.eventtus.services;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.lib.Service;
import com.gaj2l.eventtus.lib.Validation;
import com.gaj2l.eventtus.models.User;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public class UserService extends Service<User> {

    public UserService(Repository<User> repository, Validation<User> validation) {
        super(repository, validation);
    }

    /**
     * Grava usuário somente se ele não exista
     *
     * @param user
     * @return true case success
     */
    public boolean create(User user) throws Exception {
        try {
            User userSearch = this.getUserByEmail(user.getMail());
            if (userSearch == null) {
                this.store(user);
            } else {
                user.setId(userSearch.getId());
            }
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    public User getUserByEmail(String email) {
        String filters[][] = {{"mail", email}};

        List<User> users = this.list(filters);
        return (!users.isEmpty()) ? users.get(0) : null;
    }

}