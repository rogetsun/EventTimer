package com.uv.event;

import com.uv.event.impl.EventExecutorImpl;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventUtil {
    private static EventEmitter eventEmitter;

    static {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());
    }


    public static void on(String eventName, EventHandler eventHandler) {
        eventEmitter.on(eventName, eventHandler);
    }

    /**
     * 移除事件
     *
     * @param eventName
     */
    public static void remove(String eventName) {
        eventEmitter.remove(eventName);
    }

    /**
     * 触发指定事件,并给入事件对象
     *
     * @param eventName
     * @param data
     */
    public static void trigger(String eventName, JSONObject data) {
        eventEmitter.trigger(eventName, data);
    }

    public static void trigger(String eventName) {
        eventEmitter.trigger(eventName);
    }

}