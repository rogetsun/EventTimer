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
    public void deal(JSONObject data);

    /**
     * 设置事件处理器所在的事件容器对象引用
     *
     * @param eventEmitter
     */
    public void setEventEmitter(EventEmitter eventEmitter);

    /**
     * 获取事件容器引用
     *
     * @return
     */
    public EventEmitter getEventEmitter();

    /**
     * 判断本执行器是否可以执行,同时可以处理执行器属性数据
     *
     * @return 0:表示最后一次执行,执行完本次后将可以移除此事件处理器
     * 1:表示可执行
     * <0:表示不可执行,直接移除
     */
    public int canExecute();
}
