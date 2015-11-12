package com.uv.event;

/**
 * Created by uv2sun on 15/11/10.
 */
public interface EventSequence {
    public void addEventHandler(EventHandler eventHandler);

    public void removeEventHandler(EventHandler eventHandler);

}
