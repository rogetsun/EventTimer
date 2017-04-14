package com.uv.event.impl.eventForward;

import com.uv.event.Forwarder;

/**
 * Created by uv2sun on 2017/4/14.
 */
public class ForwarderImpl implements Forwarder {

    private String forwardEventName;

    public ForwarderImpl(String forwardEventName) {
        this.forwardEventName = forwardEventName;
    }

    @Override
    public String getForwardEventName() {
        return this.forwardEventName;
    }


    public void setForwardEventName(String forwardEventName) {
        this.forwardEventName = forwardEventName;
    }


}
