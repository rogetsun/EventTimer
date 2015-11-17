package com.test.event.TestMultiThreadEvent;

import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/17.
 */
public class TriggerEvent implements Runnable {

    private String eventName;

    public TriggerEvent(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public void run() {
        JSONObject jo = JSONObject.fromObject("{x:" + (int) Math.random() * 100 + "}");
        System.out.println("trigger " + eventName + ":" + jo);
        EventUtil.trigger(this.eventName, jo);
    }
}
