package com.uv.event.impl;

import com.uv.event.*;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by uv2sun on 16/9/30.
 */
public class EventAllInOneThreadExecutorImpl implements EventExecutor {
    private static final Log logger = LogFactory.getLog(EventAllInOneThreadExecutorImpl.class);
    private ExecutorService executorService;

    @Override
    public void exec(String eventName, EventHandlerQueue<EventHandler> list, JSONObject data) {
        String currentThreadName = Thread.currentThread().getName();
        logger.debug("currentThreadName:" + currentThreadName);
        if (currentThreadName.indexOf("UVEvent-" + EventUtil.EventContainerName) == 0) {
            logger.debug("当前线程为事件线程池线程，直接执行！");
            // TODO: 16/9/30 一个线程执行所有handler
            for (Iterator<EventHandler> it = list.iterator(); it.hasNext(); ) {

                EventHandler eh = it.next();
                int lastCount = eh.canExecute();
                if (lastCount <= 0) {
                    logger.debug(eh.getEventName() + " remove eventHandler " + eh.getEventHandlerID());
                    it.remove();
                    if (lastCount == 0) eh.deal(eventName, data);
                } else {
                    if (eh.beforeExec(eventName, data)) {//执行前置预处理
                        eh.deal(eventName, data);
                    }
                    eh.afterExec(eventName, data);
                }

            }
        } else {
            logger.debug("当前线程非事件线程池线程，调用线程池线程执行！");
            Runnable runnable = new EventHandlerRunnableImpl(eventName, list, data);
            this.executorService.execute(runnable);
        }

    }

    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public EventAllInOneThreadExecutorImpl(String poolName) {
        /**
         * 初始化执行器线程池
         */
        logger.debug("init Thread Pool for processor count:" + Runtime.getRuntime().availableProcessors());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new EventThreadFactory(poolName));
//        this.executorService = Executors.newCachedThreadPool();
    }

    public EventAllInOneThreadExecutorImpl(String poolName, int threadPoolSize) {
        /**
         * 初始化执行器线程池
         */
        logger.debug("init Thread Pool for size:" + threadPoolSize);
        this.executorService = Executors.newFixedThreadPool(threadPoolSize, new EventThreadFactory(poolName));
    }

    public EventAllInOneThreadExecutorImpl(String poolName, int corePoolSize, int maxPoolSize) {
        logger.debug("init Thread Pool for size:" + corePoolSize + "/" + maxPoolSize);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                new EventThreadFactory(poolName)
        );
        this.executorService = pool;
    }
}
