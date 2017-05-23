/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gaj2l.eventtus.services.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.models.User;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.loopj.android.http.SyncHttpClient;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService
{
    private static final int NOTIFICATION_ALL      = 1;
    private static final int NOTIFICATION_USER     = 2;
    private static final int NOTIFICATION_EVENT    = 3;
    private static final int NOTIFICATION_ACTIVITY = 4;

    private static final int METHOD_EVENT_UPDATE   = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        System.out.println("AQUI: "+remoteMessage.getMessageId());
        Map<String,String> data = remoteMessage.getData();
        if( data.size() > 0)
        {
            if( hasNotify(data.get("type"),data.get("value")) )
            {
                System.out.println("AQUI00: "+remoteMessage.getMessageId());

                handleNow(data.get("method"),data.get("value"));

                if (remoteMessage.getNotification() != null) {
                    System.out.println("AQUI2: "+remoteMessage.getMessageId());
                    sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
            }
            System.out.println("AQUI4: "+remoteMessage.getMessageId());
        }
    }

    private boolean hasNotify(String _type, String value)
    {
        int type = Integer.valueOf(_type);

        if( type == NOTIFICATION_ALL )
        {
            return true;
        }
        else if( type == NOTIFICATION_USER )
        {
            User user = ComponentProvider.getServiceComponent().getUserService().getUserByEmail( value );
            if( user != null )
            {
                return true;
            }
        }
        else if( type == NOTIFICATION_EVENT )
        {
            Event event = ComponentProvider.getServiceComponent().getEventService().getEventByServiceId(Long.parseLong(value));
            if( event != null )
            {
                return true;
            }
        }
        else if( type == NOTIFICATION_ACTIVITY )
        {
            Activity activity = ComponentProvider.getServiceComponent().getActivityService().getActivityByServiceId(Long.parseLong(value));
            if( activity != null )
            {
                return true;
            }
        }

        return false;
    }

    private void handleNow( String method, String value )
    {
        if( method != null )
        {
            if (Integer.valueOf(method) == METHOD_EVENT_UPDATE)
            {
                //// FIXME: 21/05/17
                System.out.println("Atualizando evento.....");
            }
        }
    }

    private void sendNotification(String title, String messageBody) {

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.eventtus)
                .setLargeIcon(BitmapFactory.decodeResource( getResources() , R.mipmap.ic_launcher_round ) )
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}