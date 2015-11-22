package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventHandler;

/**
 * Created by uv2sun on 15/11/12.
 */
public abstract class EventHandlerS implements EventHandler {
    private EventEmitter eventEmitter;
    private String eventName;
    private String desc;
    private long id;

//    @Override
//    public void deal(JSONObject data) {
//        this.deal(getEventName(), data);
//    }

    @Override
    public String getEventHandlerDesc() {
        return this.desc;
    }

    @Override
    public long getEventHandlerID() {
        return this.id;
    }

    @Override
    public void setEventHandlerDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setEventHandlerID(long id) {
        this.id = id;
    }

    @Override
    public int canExecute() {
        return 1;
    }

    @Override
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String getEventName() {
        return this.eventName;
    }

    @Override
    public void setEventEmitter(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    @Override
    public EventEmitter getEventEmitter() {
        return this.eventEmitter;
    }

    public EventHandlerS(long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public EventHandlerS() {
    }

    public EventHandlerS(long id) {
        this.id = id;
    }

}
