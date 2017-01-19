package com.uv.event.impl.executor;

import com.uv.event.EventHandlerQueue;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by uv2sun on 2017/1/19.
 * 当前实现不靠谱，放入二级缓存后无法不会正常放进EventTaskQueue执行了
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
        try {
            this.superExec(eventName, list, data);
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
                                if (this.tmpMap == null) {
                                    this.tmpMap = cache.poll();
                                }
                                if (this.tmpMap != null) {
                                    try {
                                        superExec(this.tmpMap.get("eventName").toString(), (EventHandlerQueue) this.tmpMap.get("eventHandlerQueue"), JSONObject.fromObject(this.tmpMap.get("data")));
                                        log.debug("EventTaskQueue Thread deal [" + this.tmpMap.get("eventName") + ":" + this.tmpMap.get("data") + "] queue cache, size=" + cache.size() + "");
                                        this.tmpMap = null;
                                    } catch (RejectedExecutionException e2) {
                                    }
                                }
                            }
                        }
                    });
                    this.cacheSwitchThread.start();
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
                ", cacheSwitchThread.isAlive=" + (cacheSwitchThread == null ? "false" : cacheSwitchThread.isAlive()) +
                '}';
    }

    public EventExecutorCacheImpl() {
        super();
    }

    public EventExecutorCacheImpl(int threadPoolSize) {
        super(threadPoolSize);
    }

    public EventExecutorCacheImpl(int corePoolSize, int maxPoolSize) {
        super(corePoolSize, maxPoolSize);
    }

    public EventExecutorCacheImpl(ExecutorService executorService) {
        super(executorService);
    }
}
