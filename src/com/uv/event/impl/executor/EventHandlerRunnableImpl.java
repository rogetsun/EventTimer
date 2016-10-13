package com.uv.event.impl.executor;

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
    private volatile EventHandlerQueue list;
    private volatile JSONObject data;

    @Override
    public void run() {
        if (list != null) {
            logger.debug("exec EventHandlerQueue count:" + list.size());
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Object e = it.next();
                EventHandler eh = null;
                if (e instanceof Class) {
                    try {
                        eh = (EventHandler) ((Class) e).newInstance();
                    } catch (InstantiationException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    eh = (EventHandler) e;
                }
                int lastCount = eh != null ? eh.canExecute() : 1;
                if (lastCount <= 0) {
                    logger.debug(eh.getEventName() + " remove eventHandler " + eh.getEventHandlerID());
                    it.remove();
                    if (lastCount == 0) eh.deal(eventName, data);
                } else {
                    try {
                        if (eh != null && eh.beforeExec(eventName, data)) {//执行前置预处理
                            eh.deal(eventName, data);
                            eh.afterExec(eventName, data);
                        }
                    } catch (Exception e2) {
                        logger.error("[" + eventName + "]EventHandler.deal[" + data + "]error.", e2);
                    }
                }

            }
        }

    }

    public EventHandlerRunnableImpl(String eventName, EventHandlerQueue list, JSONObject data) {
        this.eventName = eventName;
        this.list = list;
        this.data = data;
    }
}
