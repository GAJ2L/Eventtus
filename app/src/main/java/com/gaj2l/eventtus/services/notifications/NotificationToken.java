package com.gaj2l.eventtus.services.notifications;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by lucas on 27/05/17.
 */

public class NotificationToken extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println(token);
    }
}
