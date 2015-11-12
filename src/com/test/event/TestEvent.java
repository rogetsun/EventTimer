package com.test.event;

import com.uv.event.EventEmitter;
import com.uv.event.EventHandler;
import com.uv.event.EventEmitterFactory;
import com.uv.event.impl.EventExecutorImpl;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/10.
 */
public class TestEvent {
    public static void main(String[] args) throws InterruptedException {
        EventHandler eh = new MyEventHandler2();
        EventHandler eh2 = new MyEventHandler2(2);

        EventEmitter ee = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());

        ee.on("say", eh);
        ee.on("say", eh2);


        for (int i = 0; i < 10; i++) {
            JSONObject data = new JSONObject();
            data.put("name", "litx" + i);
            ee.trigger("say", data);
            Thread.sleep(2);
        }
    }
}
