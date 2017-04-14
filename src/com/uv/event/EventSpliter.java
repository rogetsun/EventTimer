package com.uv.event;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 2017/2/6.
 * 事件分发器。
 */
public interface EventSpliter {
    /**
     * 根据入参事件数据分包，返回分包后的唯一key。用于区分不同。
     * 方法内部自定义不同的分发规则。
     *
     * @param data
     * @return
     */
    Object split(JSONObject data);
}
