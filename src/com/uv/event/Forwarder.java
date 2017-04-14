package com.uv.event;

/**
 * Created by uv2sun on 2017/4/14.
 */
public interface Forwarder {

    String getForwardEventName();

    default boolean cancel() {
        return EventUtil.cancelForward(this.getForwardEventName());
    }
}
