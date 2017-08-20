package com.gaj2l.eventtus.lib;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.StrictMode;
import android.util.Base64;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.net.URL;

/**
 * Created by lucas tomasi on 03/05/17.
 */

public class Util
{
    public static DateTimeFormatter PATTERN_TIME = DateTimeFormatter.ofPattern("HH:mm");
    public static DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("dd-MM-y");
    public static DateTimeFormatter PATTERN_DATE_TIME = DateTimeFormatter.ofPattern("dd-MM-y HH:mm:ss");
    public static DateTimeFormatter PATTERN_DATE_TIME_US = DateTimeFormatter.ofPattern("y-MM-dd HH:mm:ss");

    /**
     *  converte string em um objeto do tipo offsetdatetime
     * @param date Date YYYY-MM-DD HH:MM:SS
     * @return obj OffsetDateTime
     */
    public static OffsetDateTime parse2OffsetDateTime( String date )
    {
        String dtFormatted = date.replace(' ','T' ) + "+00:00";
        return OffsetDateTime.parse( dtFormatted );
    }

    public static String getTimeFomatted( OffsetDateTime time )
    {
        return time.format(PATTERN_TIME);
    }

    public static String getDateFomatted( OffsetDateTime date )
    {
        return date.format(PATTERN_DATE);
    }

    public static String getAllDateFomatted( OffsetDateTime dateTime )
    {
        return dateTime.format(PATTERN_DATE_TIME);
    }

    public static String getAllDateFomattedUs( OffsetDateTime dateTime )
    {
        return dateTime.format(PATTERN_DATE_TIME_US);
    }

    public static  void clearBackStake(FragmentManager fragmentManager)
    {
        while ( fragmentManager.getBackStackEntryCount() > 0 )
        {
            fragmentManager.popBackStack();
            fragmentManager.executePendingTransactions();
        }
    }

    public static Bitmap getBitmap(String url) throws Exception
    {
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        URL f = new URL(url);
        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(f.openConnection().getInputStream()), 150, 150, false);

        return  bitmaptToCircleBitmap(bitmap);

    }

    public static Bitmap bitmaptToCircleBitmap( Bitmap bitmap )
    {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        Canvas c = new Canvas(circleBitmap);
        paint.setShader(shader);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        return circleBitmap;
    }


    public static Bitmap getImageBitmap(String url) throws Exception
    {
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        URL f = new URL(url);
        return Bitmap.createBitmap( BitmapFactory.decodeStream( f.openConnection().getInputStream() ) );
    }

    public static String bitmap2base64(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public  static  Bitmap base642bitmap(String base64Str )
    {
        byte[] decodedBytes = Base64.decode(base64Str.substring(base64Str.indexOf(",")  + 1) , Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
