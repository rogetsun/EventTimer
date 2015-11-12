package com.uv.event.impl;

import com.uv.event.EventEmitter;
import com.uv.event.EventExecutor;
import com.uv.event.EventHandler;
import com.uv.event.EventHandlerQueue;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uv2sun on 15/11/10.
 * 事件容器
 */
public class EventEmitterImpl implements EventEmitter {
    private Map<String, EventHandlerQueue<EventHandler>> eventPool;
    private EventExecutor eventExecutor;

    @Override
    public void on(String eventName, EventHandler eventHandler) {
        if (eventName != null && eventName.length() > 0 && eventHandler != null) {
            //给事件处理器设置其所在事件容器
            eventHandler.setEventEmitter(this);

            if (this.eventPool.containsKey(eventName)) { //如果已经存在当前事件
                EventHandlerQueue<EventHandler> list = this.eventPool.get(eventName);
                if (list == null) {//事件存在,但是事件处理器容器为null
                    list = new EventHandlerQueueImpl<EventHandler>();
                    this.eventPool.put(eventName, list);//重新添加
                }
                list.add(eventHandler);//事件处理器添加进容器
            } else {//不存在当前事件
                EventHandlerQueue<EventHandler> list = new EventHandlerQueueImpl<EventHandler>();
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
     * 从事件池提取对应时间名称的事件容器(列表)
     *
     * @param eventName
     * @return
     */
    public EventHandlerQueue<EventHandler> getEventSequence(String eventName) {
        return this.eventPool.get(eventName);
    }

    public EventEmitterImpl(EventExecutor eventExecutor) {
        this.eventPool = new HashMap<String, EventHandlerQueue<EventHandler>>();
        this.eventExecutor = eventExecutor;
    }
}
