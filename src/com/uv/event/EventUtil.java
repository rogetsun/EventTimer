package com.uv.event;

import com.uv.event.impl.EventAllInOneThreadExecutorImpl;
import com.uv.event.impl.EventExecutorImpl;
import net.sf.json.JSONObject;

import java.util.concurrent.ExecutorService;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventUtil {
    private static EventEmitter eventEmitter;
    public static String EventContainerName = "ECN";

    public static void init(int threadPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl(threadPoolSize));
    }

    public static void init(int corePoolSize, int maxPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl(corePoolSize, maxPoolSize));
    }

    public static void init() {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());
    }

    public static void initAllInOne() {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventAllInOneThreadExecutorImpl(EventContainerName));
    }

    public static void initAllInOne(int threadPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventAllInOneThreadExecutorImpl(EventContainerName, threadPoolSize));
    }

    public static void initAllInOne(int threadCorePoolSize, int threadMaxPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventAllInOneThreadExecutorImpl(EventContainerName, threadCorePoolSize, threadMaxPoolSize));
    }


    public static void on(String eventName, EventHandler eventHandler) {
        if (eventEmitter == null) {
            init();
        }
        eventEmitter.on(eventName, eventHandler);
    }

    /**
     * 移除事件
     *
     * @param eventName
     */
    public static void remove(String eventName) {
        eventEmitter.remove(eventName);
    }

    public static void remove(String eventName, EventHandler eventHandler) {
        eventEmitter.remove(eventName, eventHandler);
    }

    /**
     * 触发指定事件,并给入事件对象
     *
     * @param eventName
     * @param data
     */
    public static void trigger(String eventName, JSONObject data) {
        eventEmitter.trigger(eventName, data);
    }

    public static void trigger(String eventName) {
        eventEmitter.trigger(eventName);
    }


    public static ExecutorService getExecutorService() {
        return eventEmitter.getExecutor().getExecutorService();
    }

}
