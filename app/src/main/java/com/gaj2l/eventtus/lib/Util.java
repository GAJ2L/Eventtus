package com.gaj2l.eventtus.lib;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by lucas tomasi on 03/05/17.
 */

public class Util
{
    public static DateTimeFormatter PATTERN_TIME = DateTimeFormatter.ofPattern("HH:mm");
    public static DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("dd-MM-y");
    public static DateTimeFormatter PATTERN_DATE_TIME = DateTimeFormatter.ofPattern("dd-MM-y HH:mm");

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
}
