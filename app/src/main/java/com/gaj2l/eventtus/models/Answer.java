package com.gaj2l.eventtus.models;

/**
 * Created by artur on 6/3/17.
 */

public class Answer
{
    private Question question;
    private int option;

    public Answer( Question question, int option )
    {
        this.question = question;
        this.option = option;
    }

    public int option()
    {
        return option;
    }

}
