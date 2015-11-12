package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventHandler;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/9.
 */
public abstract class EventHandlerAbs implements EventHandler{

    private EventEmitter eventEmitter;

    private JSONObject properties;

    @Override
    public JSONObject getProperties() {
        return this.properties;
    }

    public EventHandlerAbs(JSONObject properties) {
        this.properties = properties;
    }

    @Override
    public void setProperties(JSONObject properties) {
        this.properties = properties;

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
