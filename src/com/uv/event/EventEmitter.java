package com.uv.event;

import net.sf.json.JSONObject;

import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventEmitter {

    /**
     * 注册监听指定事件名称的事件处理器
     *
     * @param eventName
     * @param eventHandler
     */
    void on(String eventName, EventHandler eventHandler);

    void on(String eventName, Class<? extends EventHandler> eventHandlerClass);

    /**
     * 移除事件全部处理器
     *
     * @param eventName
     */
    void remove(String eventName);

    /**
     * 移除事件某一个处理器
     *
     * @param eventName
     * @param eventHandler
     */
    void remove(String eventName, EventHandler eventHandler);

    void remove(String eventName, Class<EventHandler> eventHandlerClass);

    /**
     * 触发指定事件,并给入事件对象
     *
     * @param eventName
     * @param data
     */
    void trigger(String eventName, JSONObject data) throws RejectedExecutionException;

    void trigger(String eventName) throws RejectedExecutionException;

    EventExecutor getExecutor();

    Map<String, EventHandlerQueue<Object>> getEventPool();
}
