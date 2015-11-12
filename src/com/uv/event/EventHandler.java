package com.uv.event;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventHandler {
    /**
     * 事件触发时,处理事件
     * @param data
     */
    public void deal(JSONObject data);

    /**
     * 设置事件处理器所在的事件容器对象引用
     * @param eventEmitter
     */
    public void setEventEmitter(EventEmitter eventEmitter);

    /**
     * 获取事件容器引用
     * @return
     */
    public EventEmitter getEventEmitter();

    /**
     * 获取事件处理器属性
     * @return
     */
    public JSONObject getProperties();

    /**
     * 设置事件属性
     * @param properties
     */
    public void setProperties(JSONObject properties);
}
