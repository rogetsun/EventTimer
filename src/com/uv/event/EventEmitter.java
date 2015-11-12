package com.uv.event;

import net.sf.json.JSONObject;

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
    public void on(String eventName, EventHandler eventHandler);

    /**
     * 移除事件
     *
     * @param eventName
     */
    public void remove(String eventName);

    /**
     * 触发指定事件,并给入事件对象
     *
     * @param eventName
     * @param data
     */
    public void trigger(String eventName, JSONObject data);

    public void trigger(String eventName);
}
