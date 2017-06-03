package com.gaj2l.eventtus.models;

/**
 * Created by artur on 6/3/17.
 */

public class Option
{
    private int value;
    private String name;

    public Option( String name, int value )
    {
        this.name = name;
        this.value = value;
    }

    public void name( String name )
    {
        this.name = name;
    }

    public String name()
    {
        return  name;
    }

    public int value()
    {
        return value;
    }

    public void value( int value )
    {
        this.value = value;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Option )
        {
            return Option.class.cast( obj ).value() == value();
        }

        return false;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
