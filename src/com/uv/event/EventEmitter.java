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
     * 设置指定事件的分发处理规则
     *
     * @param eventName
     * @param eventSpliter
     */
    void setEventSpliter(String eventName, EventSpliter eventSpliter);

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

    /**
     * 返回是否存在指定事件名称的EventHandler
     *
     * @param eventName
     * @return
     */
    boolean containEventHandler(String eventName);

    /**
     * 返回是否存在指定事件名称的事件拦截转发器
     *
     * @param eventName
     * @return
     */
    boolean containEventForwarder(String eventName);

    /**
     * 增加对某一个事件下一次触发时的事件转发器。转发一次后将失效。
     *
     * @param eventName
     * @param eventOnceForwarder
     * @return 事件拦截转发器
     */
    Forwarder forwardOnce(String eventName, EventOnceForwarder eventOnceForwarder);


    /**
     * 取消对某一事件上的所有事件拦截转发器
     *
     * @param eventName
     * @return true:移除成功；false:事件拦截转发器已不存在
     */
    boolean cancelForward(String eventName);

}
