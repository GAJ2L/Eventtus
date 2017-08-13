package com.gaj2l.eventtus.view.controllers;

/**
 * Created by artur on 13/08/17.
 */

public interface  EventListener<T>
{
    public void onEvent( T source ) throws Exception;
}
