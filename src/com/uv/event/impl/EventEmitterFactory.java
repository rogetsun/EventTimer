package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventExecutor;

/**
 * Created by uv2sun on 15/11/10.
 */
public class EventEmitterFactory {
    public static EventEmitter getEventEmitter(EventExecutor eventExecutor) {
        return new EventEmitterImpl(eventExecutor);
    }
}
