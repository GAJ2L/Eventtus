package com.gaj2l.eventtus.services.notifications;

import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.services.web.TokenWebService;
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
        TokenWebService.save(Session.getInstance(getApplicationContext()).getString("email"));
    }
}
