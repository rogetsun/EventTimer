package com.uv.timer;

import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

import java.util.TimerTask;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventTriggerTask extends TimerTask {

    private String eventName;
    private JSONObject data;

    @Override
    public void run() {
        EventUtil.trigger(eventName, data);
    }

    public EventTriggerTask(String eventName) {
        this.eventName = eventName;
    }

    public EventTriggerTask(String eventName, JSONObject data) {
        this.eventName = eventName;
        this.data = data;
    }
}
