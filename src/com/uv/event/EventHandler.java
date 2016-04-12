package com.uv.event;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/9.
 * 事件处理器
 * E 范型,为事件处理器处理的数据的类型
 * P 范型,为事件处理器的属性泛型
 */
public interface EventHandler {
    /**
     * 事件触发时,处理事件
     *
     * @param data
     */
//    void deal(JSONObject data);

    /**
     * 用指定事件名称执行事件处理器逻辑
     *
     * @param eventName
     * @param data
     */
    void deal(String eventName, JSONObject data);

    /**
     * 设置事件处理器所在的事件容器对象引用
     *
     * @param eventEmitter
     */
    void setEventEmitter(EventEmitter eventEmitter);

    /**
     * 获取事件容器引用
     *
     * @return
     */
    EventEmitter getEventEmitter();

    /**
     * 判断本执行器是否可以执行,同时可以处理执行器属性数据
     *
     * @return 0:表示最后一次执行,执行完本次后将可以移除此事件处理器
     * 1:表示可执行
     * <0:表示不可执行,直接移除
     */
    int canExecute();

    /**
     * 设置触发此事件处理器的事件名称
     *
     * @param eventName
     */
    void setEventName(String eventName);

    /**
     * 获得触发此事件处理器的事件名称
     *
     * @return
     */
    String getEventName();


    /**
     * 设置事件处理器ID
     *
     * @param id
     */
    void setEventHandlerID(long id);

    /**
     * 设置事件处理器描述
     *
     * @param desc
     */
    void setEventHandlerDesc(String desc);

    /**
     * 获得事件处理器的ID
     *
     * @return
     */
    long getEventHandlerID();

    /**
     * 获得事件处理器描述
     *
     * @return
     */
    String getEventHandlerDesc();

    /**
     * 执行前要做的事,可用于前置执行条件判断或者预处理数据
     *
     * @param data 事件触发时给予的数据
     * @return true 继续执行,false 执行将被取消
     */
    boolean beforeExec(String eventName, JSONObject data);

    /**
     * 执行完后要做的事情
     *
     * @param data 事件触发时给予的数据, 可能deal已经处理
     */
    void afterExec(String eventName, JSONObject data);
}
