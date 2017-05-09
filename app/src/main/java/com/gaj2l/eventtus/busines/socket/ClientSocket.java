package com.gaj2l.eventtus.busines.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by lucas on 07/05/17.
 */

public abstract class ClientSocket
        extends
        Thread
{
    private static final String  HOST = "45.55.153.203";
    private static final Integer PORT = 5000;
    private static Socket  socket;
    private static DataOutputStream out;
    private static DataInputStream in;

    public ClientSocket()
    {
        try {
            socket = new Socket( HOST,PORT );
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDaemon(true);
    }

    public void send( String source ) throws Exception
    {
        if ( source != null && out != null )
        {
                out.writeUTF(source);
        }
    }

    public abstract void onRecive( String  data ) throws Exception;

    @Override
    public void run()
    {
        try
        {
            out    = new DataOutputStream(socket.getOutputStream());
            in     = new DataInputStream(socket.getInputStream());
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        try
        {
            while ( true )
            {
                Thread.sleep(1000);
                byte[] b = new byte[8];
                in.read(b);
                String retorno = new String(b, "UTF-8").substring(0,7);
                if( retorno.equalsIgnoreCase("recived") )
                {
                    onRecive( retorno );
                }
            }
        }

        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }


    public static boolean isRunning() {
        try (Socket s = new Socket(HOST, PORT)) {
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}