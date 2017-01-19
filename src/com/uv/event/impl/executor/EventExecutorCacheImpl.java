package com.uv.event.impl.executor;

import com.uv.event.EventHandlerQueue;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * Created by uv2sun on 2017/1/19.
 */
public class EventExecutorCacheImpl extends EventExecutorImpl {
    private static final Log log = LogFactory.getLog(EventExecutorCacheImpl.class);
    private TransferQueue<Map<String, Object>> cache = new LinkedTransferQueue<>();
    private Thread cacheSwitchThread;

    private void superExec(String eventName, EventHandlerQueue queue, JSONObject data) throws RejectedExecutionException {
        super.exec(eventName, queue, data);
    }

    @Override
    public void exec(String eventName, EventHandlerQueue list, JSONObject data) throws RejectedExecutionException {
        if (this.cache.size() > 0) {
            this.cache.add(toMap(eventName, list, data));
        } else {
            try {
                super.exec(eventName, list, data);
            } catch (RejectedExecutionException e) {
                log.debug("EventTaskQueue is full, forward to The twice cache;");
                this.cache.add(toMap(eventName, list, data));
                log.debug("EventTaskQueue twice cache size:" + this.cache.size());
                synchronized (this) {
                    if (this.cacheSwitchThread == null || !this.cacheSwitchThread.isAlive()) {
                        this.cacheSwitchThread = new Thread(new Runnable() {
                            private Map<String, Object> tmpMap;

                            @Override
                            public void run() {
                                while (cache.size() > 0) {
                                    try {
                                        if (this.tmpMap == null) {
                                            this.tmpMap = cache.poll(1, TimeUnit.SECONDS);
                                        }
                                        if (this.tmpMap != null) {
                                            try {
                                                superExec(this.tmpMap.get("eventName").toString(), (EventHandlerQueue) this.tmpMap.get("eventHandlerQueue"), JSONObject.fromObject(this.tmpMap.get("data")));
                                                this.tmpMap = null;
                                            } catch (RejectedExecutionException e2) {
                                            }
                                        }
                                    } catch (InterruptedException e1) {
                                    }
                                }
                            }
                        });
                        this.cacheSwitchThread.start();
                    }
                }
            }
        }
    }


    private Map<String, Object> toMap(String eventName, EventHandlerQueue queue, JSONObject data) {
        Map<String, Object> m = new HashMap<>();
        m.put("eventName", eventName);
        m.put("eventHandlerQueue", queue);
        m.put("data", data);
        return m;
    }


    @Override
    public String toString() {
        return "EventExecutorCacheImpl{" +
                "ExecutorService=" + this.getExecutorService().toString() +
                ", twice cache.size=" + cache.size() +
                ", cacheSwitchThread.isAlive=" + cacheSwitchThread.isAlive() +
                '}';
    }
}
