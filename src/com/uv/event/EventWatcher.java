package com.uv.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by uv2sun on 2016/12/8.
 */
public class EventWatcher implements Runnable {
    private boolean isStop = false;

    private volatile static boolean singletonFlag = false;
    private static final Log log = LogFactory.getLog(EventWatcher.class);

    @Override
    public void run() {
        if (!singletonFlag) {
            log.debug("EventWatcher start...");
            singletonFlag = true;
            while (!isStop) {

                log.debug(EventUtil.getExecutorService());
                Map<String, EventHandlerQueue<Object>> ep = EventUtil.getEventPool();
                String[] keys = (String[]) ep.keySet().toArray();
                for (String key : keys) {
                    StringBuffer sb = new StringBuffer();
                    EventHandlerQueue queue = ep.get(key);
                    for (Iterator it = queue.iterator(); it.hasNext(); ) {
                        Object o = it.next();
                        if (o instanceof Class) {
                            sb.append(o.toString() + ",");
                        } else if (o instanceof EventHandler) {
                            sb.append(o.getClass() + ",");
                        }
                    }
                    log.debug("EventWatcher:EventName[" + key + "],EventHandler{" + sb.toString() + "}");
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("EventWatcher is Interrupted", e);
                    break;
                }
            }
        } else {
            log.debug("EventWatcher Already Started");
        }
    }

    public void stop() {

    }
}
