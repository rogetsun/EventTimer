package com.test.event;

import com.uv.event.EventEmitter;
import com.uv.event.EventEmitterFactory;
import com.uv.event.EventHandler;
import com.uv.event.impl.EventExecutorImpl;

/**
 * Created by uv2sun on 15/11/12.
 */
public class TestEvent2 {
    public static void main(String[] args) {
        EventHandler eh = new MyEventHandler2(1L);
        EventHandler eh2 = new MyEventHandler2(2L);

        EventEmitter ee = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());

        ee.on("fuck you", eh);
        ee.on("fuck you", eh2);
        ee.on("hello", eh2);
        ee.on("hello", eh);

        ee.trigger("fuck you");
        ee.trigger("hello");
    }
}
