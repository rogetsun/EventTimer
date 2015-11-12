package com.test.event;

import com.uv.event.EventEmitter;
import com.uv.event.impl.EventEmitterFactory;
import com.uv.event.impl.EventExecutorImpl;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/10.
 */
public class TestEvent {
    public static void main(String[] args) throws InterruptedException {
        MyEventHandler me = new MyEventHandler(JSONObject.fromObject("{exec_count:1}"));

        EventEmitter ee = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());

        ee.on("say", me);

        ee.trigger("say", JSONObject.fromObject("{name:'litx'}"));
//        Thread.sleep(2000);
        ee.trigger("say", JSONObject.fromObject("{name:'litx'}"));
    }
}
