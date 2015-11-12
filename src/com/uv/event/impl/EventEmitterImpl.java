package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventExecutor;
import com.uv.event.EventHandler;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by uv2sun on 15/11/10.
 * 事件容器
 */
public class EventEmitterImpl implements EventEmitter {
    private Map<String, List<EventHandler>> eventPool;
    private EventExecutor eventExecutor;

    @Override
    public void on(String eventName, EventHandler eventHandler) {
        if (eventName != null && eventName.length() > 0 && eventHandler != null) {
            //给事件处理对象设置其所在事件容器
            eventHandler.setEventEmitter(this);
            if (this.eventPool.containsKey(eventName)) { //如果已经存在当前事件
                List<EventHandler> list = this.eventPool.get(eventName);
                if (list == null) {
                    list = new ArrayList<EventHandler>();
                    this.eventPool.put(eventName, list);
                }
                list.add(eventHandler);
            } else {//不存在当前事件
                List<EventHandler> list = new ArrayList<EventHandler>();
                list.add(eventHandler);
                this.eventPool.put(eventName, list);
            }
        }
    }

    @Override
    public void remove(String eventName) {
        if (eventName != null && eventName.length() > 0) {
            this.eventPool.remove(eventName);
        }
    }

    @Override
    public void trigger(String eventName, JSONObject data) {
        this.eventExecutor.exec(this.getEventSequence(eventName), data);
    }

    @Override
    public void trigger(String eventName) {
        this.trigger(eventName, null);
    }

    /**
     * 从事件池提取对应时间名称的时间队列
     *
     * @param eventName
     * @return
     */
    public List<EventHandler> getEventSequence(String eventName) {
        return this.eventPool.get(eventName);
    }

    public EventEmitterImpl(EventExecutor eventExecutor) {
        this.eventPool = new HashMap<String, List<EventHandler>>();
        this.eventExecutor = eventExecutor;
    }
}
