package com.uv.event.impl;

import com.uv.event.EventHandler;
import com.uv.event.EventHandlerQueue;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

/**
 * Created by uv2sun on 16/9/30.
 */
public class EventHandlerRunnableImpl implements Runnable {
    private static final Log logger = LogFactory.getLog(EventHandlerRunnableImpl.class);
    private volatile String eventName;
    private volatile EventHandlerQueue<EventHandler> list;
    private volatile JSONObject data;

    @Override
    public void run() {
        if (list != null) {
            logger.debug("exec EventHandlerQueue count:" + list.size());
            for (Iterator<EventHandler> it = list.iterator(); it.hasNext(); ) {
                EventHandler eh = it.next();
                int lastCount = eh.canExecute();
                if (lastCount <= 0) {
                    logger.debug(eh.getEventName() + " remove eventHandler " + eh.getEventHandlerID());
                    it.remove();
                    if (lastCount == 0) eh.deal(eventName, data);
                } else {
                    try {
                        if (eh.beforeExec(eventName, data)) {//执行前置预处理
                            eh.deal(eventName, data);
                        }
                        eh.afterExec(eventName, data);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }

            }
        }

    }

    public EventHandlerRunnableImpl(String eventName, EventHandlerQueue<EventHandler> list, JSONObject data) {
        this.eventName = eventName;
        this.list = list;
        this.data = data;
    }
}
