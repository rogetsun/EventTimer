package com.uv.event;

import java.util.Iterator;

/**
 * Created by uv2sun on 15/11/12.
 * 事件处理器列表
 */
public interface EventHandlerQueue<E> {
    /**
     * 返回列表长度
     *
     * @return
     */
    int size();

    /**
     * 返回列表迭代器
     *
     * @return
     */
    Iterator<E> iterator();

    /**
     * 添加一个元素到列表
     *
     * @param e
     */
    void add(E e);

    /**
     * 从移除一个元素
     *
     * @param e
     */
    void remove(E e);

}
