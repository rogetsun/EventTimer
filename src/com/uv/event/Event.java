package com.uv.event;

/**
 * Created by uv2sun on 15/11/9.
 */
public interface Event {
    /**
     * 获取事件对象的数据
     *
     * @param key 数据key
     * @return 数据value
     */
    public Object getData(String key);

    public void putData(String key, Object value);
}
