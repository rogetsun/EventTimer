package com.uv.timer;

import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventTriggerTask extends TimerTask {

    private String eventName;
    private JSONObject data;

    @Override
    public void run() {
        try {
            EventUtil.trigger(eventName, data);
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    public EventTriggerTask(String eventName) {
        this.eventName = eventName;
    }

    public EventTriggerTask(String eventName, JSONObject data) {
        this.eventName = eventName;
        this.data = data;
    }
}
