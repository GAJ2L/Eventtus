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

    public UserService(Repository<User> repository, Validation<User> validation)
    {
        super(repository, validation);
    }

    /**
     * Grava usuário somente se ele não exista
     * @param user
     * @return true case success
     */
    public boolean create( User user )
    {
        try
        {
            if( getUserByProvider(user.getMail(), user.getMethodAutentication()) != null )
            {
                store(user);
            }
        }
        catch (Exception e)
        {
                return false;
        }

        return true;
    }

    public User getUserByProvider( String email, String provider )
    {
        String filters[][] = { { "mail" , email } , { "methodAutentication" , provider  } };

        List<User> users = this.getRepository().get( filters );
        return (!users.isEmpty())? users.get(0) : null ;
    }

}