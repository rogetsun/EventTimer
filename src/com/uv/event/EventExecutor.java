package com.uv.event;

import net.sf.json.JSONObject;

import java.util.concurrent.ExecutorService;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventExecutor {
    /**
     * 执行事件队列
     *
     * @param list 要执行的事件列表
     * @param data 事件触发时携带的数据
     */
//    public void exec(EventHandlerQueue<EventHandler> list, JSONObject data);

    /**
     * 执行事件队列
     *
     * @param eventName 事件名称
     * @param list      要执行的事件列表
     * @param data      事件触发时携带的数据
     */
    void exec(String eventName, EventHandlerQueue<EventHandler> list, JSONObject data);

    ExecutorService getExecutorService();

}
