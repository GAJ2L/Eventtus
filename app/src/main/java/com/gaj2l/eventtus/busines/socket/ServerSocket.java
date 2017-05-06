package com.gaj2l.eventtus.busines.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by artur on 5/4/17.
 */

public class ServerSocket
        extends
        Thread
{
    private String source;

    private static final String HOST  = "224.0.0.2";
    private static final Integer PORT = 5555;

    public ServerSocket()
    {
        setDaemon(true);
    }

    public void send( String source )
    {
        this.source = source;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                Thread.sleep( 10 );

                if ( source != null )
                {
                    byte[] b = source.getBytes();

                    InetAddress addr   = InetAddress.getByName( HOST );

                    DatagramSocket ds  = new DatagramSocket();

                    DatagramPacket pkg = new DatagramPacket( b, b.length, addr, PORT );

                    ds.send( pkg );

                    source = null;
                }
            }

            catch ( InterruptedException | IOException e )
            {
                e.printStackTrace();
            }
        }
    }
}
