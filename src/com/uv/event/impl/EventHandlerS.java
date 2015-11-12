package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventHandler;

/**
 * Created by uv2sun on 15/11/12.
 */
public abstract class EventHandlerS implements EventHandler {
    private EventEmitter eventEmitter;

    @Override
    public int canExecute() {
        return 1;
    }

    @Override
    public void setEventEmitter(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    @Override
    public EventEmitter getEventEmitter() {
        return this.eventEmitter;
    }
}
