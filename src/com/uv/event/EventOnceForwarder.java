package com.uv.event;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 2017/4/6.
 * 事件转发接口，一次性的改变原有事件触发到另一个事件上
 */
@FunctionalInterface
public interface EventOnceForwarder {
    /**
     * @param eventName 原有事件名
     * @param data      事件数据
     * @return 改变触发的事件名
     */
    String forward(String eventName, JSONObject data);
}
