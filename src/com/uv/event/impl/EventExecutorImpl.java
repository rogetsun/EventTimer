package com.uv.event.impl;

import com.uv.event.EventExecutor;
import com.uv.event.EventHandler;
import com.uv.event.EventHandlerQueue;
import com.uv.timer.TimerUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by uv2sun on 15/11/10.
 */
public class EventExecutorImpl implements EventExecutor {

    private ExecutorService executorService;
    private Log log = LogFactory.getLog(EventExecutorImpl.class);

//
//    @Override
//    public void exec(final EventHandlerQueue<EventHandler> list, final JSONObject data) {
//        Runnable runnable = new Runnable() {
//            private Log log = LogFactory.getLog("");
//
//            @Override
//            public void run() {
//                for (Iterator<EventHandler> it = list.iterator(); it.hasNext(); ) {
//                    EventHandler eh = it.next();
//                    int lastCount = eh.canExecute();
//                    if (lastCount <= 0) {
//                        log.debug(eh.getEventName() + " remove eventHandler " + eh.getEventHandlerID());
//                        it.remove();
//                        if (lastCount == 0) eh.deal(data);
//                    } else {
//                        eh.deal(data);
//                    }
//
//                }
//
//            }
//        };
//
//        this.executorService.execute(runnable);
//    }

    @Override
    public void exec(final String eventName, final EventHandlerQueue<EventHandler> list, final JSONObject data) {
        Runnable runnable = new EventHandlerRunnableImpl(eventName, list, data);
        log.debug("run EventHandlerRunnableImpl:" + eventName + ",list.count:" + list.size() + ",data:" + data);
        this.executorService.execute(runnable);
    }

    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }


    public EventExecutorImpl() {
        /**
         * 初始化执行器线程池
         */
        System.out.println("init Thread Pool for " + Runtime.getRuntime().availableProcessors());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public EventExecutorImpl(int threadPoolSize) {
        /**
         * 初始化执行器线程池
         */
        System.out.println("init Thread Pool for " + threadPoolSize);
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public EventExecutorImpl(int corePoolSize, int maxPoolSize) {
        System.out.println("init Thread Pool for " + corePoolSize + "/" + maxPoolSize);
        this.executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5000));
    }
}
