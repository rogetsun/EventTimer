package com.uv.event;

import com.uv.event.impl.executor.EventAllInOneThreadExecutorImpl;
import com.uv.event.impl.executor.EventExecutorImpl;
import com.uv.timer.TimerUtil;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by uv2sun on 15/11/12.
 */
public class EventUtil {
    private static EventEmitter eventEmitter;
    public static String EventContainerName = "ECN";
    public static int taskRejectCount = 100;
    public static final String EVENT_SEP = "@";

    public static final String EVENT_PROPAGATION_SEQ = "|";

    public static final String EVENT_NO_PROPAGATION_SEQ = "@";

    public static void init(int threadPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl(threadPoolSize));
    }

    public static void init(int corePoolSize, int maxPoolSize) {
        eventEmitter = EventEmitterFactory.getEventEmitter(new EventExecutorImpl(corePoolSize, maxPoolSize));
    }

    public static void init(EventExecutor eventExecutor) {
        eventEmitter = EventEmitterFactory.getEventEmitter(eventExecutor);
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


    public static void setEventSpliter(String eventName, EventSpliter eventSpliter) {
        if (eventEmitter == null) {
            init();
        }
        eventEmitter.setEventSpliter(eventName, eventSpliter);
    }

    public static void on(String eventName, EventHandler eventHandler) {
        if (eventEmitter == null) {
            init();
        }
        eventEmitter.on(eventName, eventHandler);
    }

    public static void on(String eventName, Class<? extends EventHandler> eventHandlerClass) {
        if (eventEmitter == null) {
            init();
        }
        eventEmitter.on(eventName, eventHandlerClass);
    }

    /**
     * 移除事件
     *
     * @param eventName
     */
    public static void remove(String eventName) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(className + "." + methodName + ":" + lineNumber + " call remove event:" + eventName);
        eventEmitter.remove(eventName);
    }

    public static void remove(String eventName, EventHandler eventHandler) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(className + "." + methodName + ":" + lineNumber + " call remove event:" + eventName + ", eventHandler:" + eventHandler);
        eventEmitter.remove(eventName, eventHandler);
    }

    public static void remove(String eventName, Class<EventHandler> eventHandlerClass) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        System.out.println(className + "." + methodName + ":" + lineNumber + " call remove event:" + eventName + ", eventHandler.class:" + eventHandlerClass.getName());
        eventEmitter.remove(eventName, eventHandlerClass);
    }

    /**
     * 触发指定事件,并给入事件对象
     *
     * @param eventName
     * @param data
     */
    public static void trigger(String eventName, JSONObject data) throws RejectedExecutionException {
//        if (eventEmitter == null) {
//            init();
//        }
        eventEmitter.trigger(eventName, data);
    }

    public static void trigger(String eventName) throws RejectedExecutionException {
//        if (eventEmitter == null) {
//            init();
//        }
        eventEmitter.trigger(eventName);
    }


    public static ExecutorService getExecutorService() {
        return eventEmitter.getExecutor().getExecutorService();
    }

    public static String getEventQueueInfo() {
        return eventEmitter.toString();
    }

    public static Map<String, EventHandlerQueue<Object>> getEventPool() {
        return eventEmitter.getEventPool();
    }

    public static Forwarder forwardOnce(String eventName, EventOnceForwarder eventOnceForwarder) {
        return eventEmitter.forwardOnce(eventName, eventOnceForwarder);
    }

    public static boolean cancelForward(String eventName) {
        return eventEmitter.cancelForward(eventName);
    }

    public static boolean containEventHandler(String eventName) {
        return eventEmitter.containEventHandler(eventName);
    }

    public static boolean containEventForwarder(String eventName) {
        return eventEmitter.containEventForwarder(eventName);
    }

    public static void shutdown() {
        eventEmitter.shutdown();
        TimerUtil.shutdown();
    }
}
