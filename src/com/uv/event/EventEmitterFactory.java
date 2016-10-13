package com.uv.event;

import com.uv.event.impl.eventEmitter.EventEmitterImpl;

/**
 * Created by uv2sun on 15/11/10.
 */
public class EventEmitterFactory {
    public static EventEmitter getEventEmitter(EventExecutor eventExecutor) {
        return new EventEmitterImpl(eventExecutor);
    }
}
