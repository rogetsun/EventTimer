package com.uv.event;

import net.sf.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventExecutor {

    /**
     * 执行事件队列
     *
     * @param eventName 事件名称
     * @param list      要执行的事件列表
     * @param data      事件触发时携带的数据
     */
    void exec(String eventName, EventHandlerQueue list, JSONObject data) throws RejectedExecutionException;

    ExecutorService getExecutorService();

    void setExecutorService(ExecutorService executorService);

    void shutdown();
}
