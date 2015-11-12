package com.uv.event;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface EventExecutor {
    /**
     * 执行事件队列
     *
     * @param list
     */
    public void exec(List<EventHandler> list, JSONObject event);
}
