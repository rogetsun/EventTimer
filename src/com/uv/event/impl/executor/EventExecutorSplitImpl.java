package com.uv.event.impl.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by uv2sun on 2017/2/6.
 */
public class EventExecutorSplitImpl extends EventExecutorImpl {
    /**
     * 特定事件的执行器
     */
    private String eventName;
    /**
     * 事件分发后特定通道key标识
     */
    private Object key;

    /**
     * 事件执行队列
     */
    private ExecutorService executorService;

    public EventExecutorSplitImpl(String eventName, Object key) {
        this.executorService = Executors.newSingleThreadExecutor(new EventThreadFactory(eventName + "-Pool"));
        this.eventName = eventName;
        this.key = key;
    }

    @Override
    public String toString() {
        return "EventExecutorSplitImpl{" +
                "eventName='" + eventName + '\'' +
                ", key=" + key +
                ", executorService=" + executorService +
                '}';
    }
}
