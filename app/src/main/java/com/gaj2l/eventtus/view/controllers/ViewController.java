package com.gaj2l.eventtus.view.controllers;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.gaj2l.eventtus.view.fragments.DetailEventFragment;
import com.gaj2l.eventtus.view.fragments.EventFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by artur on 13/08/17.
 */

public class ViewController
{
    public static void redirectEvents(FragmentManager manager, Context context ) throws Exception
    {

        try
        {
            List<Event> events = ComponentProvider.getServiceComponent().getEventService()
                    .getEventsByUser(Session.getInstance(context).getLong("user"));

            manager.popBackStack();

            if ( events != null && events.size() == 1 )
            {
                DetailEventFragment fragment = new DetailEventFragment();
                fragment.setEvent( events.get(0) );
                manager.beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailEventFragment").commit();
            }

            else
            {
                manager.beginTransaction().replace(R.id.fragment, new EventFragment()).addToBackStack("EventFragment").commit();
            }
        }

        catch ( Exception e )
        {
            e.printStackTrace();

            Message.error( context, R.string.error );
        }
    }

    public static void showInputCode( final Context c, final EventListener listener )
    {
        final EditText input = new EditText(c);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setView(input);
        alertDialog.setTitle(R.string.message_code);
        alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                if (Internet.isConnect(c))
                {
                    final Preload p = new Preload(c);
                    p.show();
                    EventWebService.getEvent(Session.getInstance(c).getString("email"), input.getText().toString(), new EventWebService.ActionEvent()
                    {
                        @Override
                        public void onEvent(Event event)
                        {
                            try {
                                p.dismiss();
                                listener.onEvent(null);
                                int msg = (event != null) ? R.string.add_event_success : R.string.add_event_error;
                                Message.show(c, msg);
                            }
                            catch ( Exception e )
                            {
                                e.printStackTrace();
                                Message.error( c, R.string.error );
                            }
                        }
                    });
                }
                else
                {
                    Message.show(c, R.string.err_conection);
                }
            }
        });

        alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {}
        });

        alertDialog.show();
    }



}
