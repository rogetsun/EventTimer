package com.uv.event.impl.executor;

import com.uv.event.EventExecutor;
import com.uv.event.EventHandlerQueue;
import com.uv.event.EventUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    public EventExecutorImpl() {
        /**
         * 初始化执行器线程池
         */
        log.debug("init Thread Pool for " + Runtime.getRuntime().availableProcessors());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new EventThreadFactory(EventUtil.EventContainerName));
//        this.executorService = Executors.newCachedThreadPool();
    }

    public EventExecutorImpl(int threadPoolSize) {
        /**
         * 初始化执行器线程池
         */
        log.debug("init Thread Pool for " + threadPoolSize);
        this.executorService = Executors.newFixedThreadPool(threadPoolSize, new EventThreadFactory(EventUtil.EventContainerName));

    }

    public EventExecutorImpl(int corePoolSize, int maxPoolSize) {
        log.debug("init Thread Pool for " + corePoolSize + "/" + maxPoolSize);
        int queueSize = maxPoolSize > 50 ? maxPoolSize * 200 : 10000;
        this.executorService =
                new ThreadPoolExecutor(corePoolSize, maxPoolSize, 30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(queueSize),
                        new EventThreadFactory(EventUtil.EventContainerName));
    }

    public EventExecutorImpl(ExecutorService executorService) {
        log.debug("init Thread Pool By " + executorService);
        this.executorService = executorService;
    }

    @Override
    public void exec(final String eventName, final EventHandlerQueue list, final JSONObject data) throws RejectedExecutionException {
        Runnable runnable = null;
        try {
            log.debug("run EventHandlerRunnableImpl:" + eventName + ",list.count:" + (list == null ? 0 : list.size()) + ",data:" + data);
            if (list != null && list.size() > 0) {
                runnable = new EventHandlerRunnableImpl(eventName, list, data);
                this.executorService.execute(runnable);
            }
        } catch (Throwable e) {
            log.error(this.getExecutorService());
            log.error("add " + eventName + " to deal queue error.", e);
            int c = EventUtil.taskRejectCount;
            boolean ok = false;
            while (c > 0 && !ok) {
                try {
                    log.debug("re " + c + " times execute event:" + eventName);
                    this.executorService.execute(runnable);
                    ok = true;
                } catch (RejectedExecutionException e1) {
                    c--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e2) {
//                        e2.printStackTrace();
                    }
                }
            }

            if (!ok) throw e;
        }
    }

    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    @Override
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void shutdown() {
        this.executorService.shutdown();
    }

    @Override
    public String toString() {
        return "EventExecutorImpl{" +
                "executorService=" + executorService.toString() +
                '}';
    }
}
