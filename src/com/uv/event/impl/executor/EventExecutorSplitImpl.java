package com.uv.event.impl.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by uv2sun on 2017/2/6.
 */
public class EventExecutorSplitImpl extends EventExecutorImpl {
    private static final Log log = LogFactory.getLog(EventExecutorSplitImpl.class);
    /**
     * 特定事件的执行器
     */
    private String eventName;
    /**
     * 事件分发后特定通道key标识
     */
    private Object key;

    public EventExecutorSplitImpl(String eventName, Object key) {
        super(Executors.newFixedThreadPool(1, new EventThreadFactory(eventName + "@" + key)));
        this.eventName = eventName;
        this.key = key;
        log.debug("init " + eventName + "@" + key.toString() + " Thread Pool for 1 ok");
    }

    @Override
    public String toString() {
        return "EventExecutorSplitImpl{" +
                "eventName='" + eventName + '\'' +
                ", key=" + key +
                ", executorService=" + this.getExecutorService().toString() +
                '}';
    }
}
