package com.uv.event.impl;

import com.uv.event.EventExecutor;
import com.uv.event.EventHandler;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by uv2sun on 15/11/10.
 */
public class EventExecutorImpl implements EventExecutor {

    private ExecutorService executorService;

    @Override
    public void exec(List<EventHandler> list, JSONObject data) {
        final UserData userData = new UserData(list, data);
        Runnable runnable = new Runnable() {
            private Log log = LogFactory.getLog("");

            @Override
            public void run() {
                log.debug("begin to executor");
                JSONObject data = userData.getData();
                List<EventHandler> list = userData.getEventHandlerList();
                for (EventHandler eh : list) {
                    eh.deal(data);
                    JSONObject jo = eh.getProperties();
                    if (jo.containsKey("exec_count")) {
                        int ec = jo.getInt("exec_count");
                        jo.put("exec_count", --ec);
                        if (ec <= 0) {
                            log.debug("remove");
                            userData.removeEventHandler(eh);
                        }
                    }
                }

                log.debug("end executor");
            }
        };

        this.executorService.execute(runnable);
    }


    /**
     * 内部类,用于线程数据共享
     */
    class UserData {
        private List<EventHandler> eventHandlerList;
        private JSONObject data;

        public UserData(List<EventHandler> list, JSONObject data) {
            this.eventHandlerList = list;
            this.data = data;
        }

        public List<EventHandler> getEventHandlerList() {
            return this.eventHandlerList;
        }

        public JSONObject getData() {
            return this.data;
        }

        public synchronized void removeEventHandler(EventHandler eventHandler) {
            this.eventHandlerList.remove(eventHandler);
        }

    }

    public EventExecutorImpl() {
        /**
         * 初始化执行器线程池
         */
        this.executorService = Executors.newCachedThreadPool();
    }
}
