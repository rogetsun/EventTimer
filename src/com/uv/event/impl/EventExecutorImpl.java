package com.uv.event.impl;

import com.uv.event.EventExecutor;
import com.uv.event.EventHandler;
import com.uv.event.EventHandlerQueue;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (list != null) {
                    log.debug("exec EventHandlerQueue count:" + list.size());
                    for (Iterator<EventHandler> it = list.iterator(); it.hasNext(); ) {
                        EventHandler eh = it.next();
                        int lastCount = eh.canExecute();
                        if (lastCount <= 0) {
                            log.debug(eh.getEventName() + " remove eventHandler " + eh.getEventHandlerID());
                            it.remove();
                            if (lastCount == 0) eh.deal(eventName, data);
                        } else {
                            eh.deal(eventName, data);
                        }

                    }
                }

            }
        };

        this.executorService.execute(runnable);
    }


    public EventExecutorImpl() {
        /**
         * 初始化执行器线程池
         */
        this.executorService = Executors.newCachedThreadPool();
    }
}
