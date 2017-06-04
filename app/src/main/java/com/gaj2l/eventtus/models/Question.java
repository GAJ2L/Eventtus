package com.gaj2l.eventtus.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artur on 6/3/17.
 */

public class Question
{
    private List<Option> options;
    private String name;
    private int id;

    public Question( String name )
    {
        this.name = name;
    }

    public void name( String name )
    {
        this.name = name;
    }

    public void id( int id ) { this.id = id; }

    public int id() { return id; }

    public String name()
    {
        return  name;
    }

    public List<Option> options()
    {
        return options;
    }

    public void options( List<Option> options )
    {
        this.options = options;
    }

    public void options( Option option )
    {
        if ( options == null )
        {
            options = new ArrayList();
        }

        options.add( option );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Question )
        {
            return Question.class.cast( obj ).id() == id();
        }

        return false;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
