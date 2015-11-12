package com.uv.event;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventExecutor {
    /**
     * 执行事件队列
     *
     * @param list
     */
    public void exec(EventHandlerQueue<EventHandler> list, JSONObject event);
}
