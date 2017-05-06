package com.gaj2l.eventtus.lib;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.gaj2l.eventtus.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by lucas on 05/05/17.
 */

public abstract class Download extends AsyncTask<String, String, String>
{
    private View view;
    private String url;
    private String name;

    public Download(View view , String url, String name)
    {
        this.view = view;
        this.url  = url;
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if( view != null )
        {
            Snackbar.make(view, R.string.downloading ,Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try
        {
            URL u =  new URL(url);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name);
            FileOutputStream fos = new FileOutputStream(f);
            System.out.println(f.getAbsolutePath());
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }
            fos.close();

            if( view != null )
            {
                Snackbar.make(view, R.string.downloading_success ,Snackbar.LENGTH_SHORT).show();
            }

            onEvent(f);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            if( view != null )
            {
                Snackbar.make(view, R.string.downloading_error ,Snackbar.LENGTH_SHORT).show();
            }
        }

        return null;
    }

    public abstract void onEvent( File file );

}
