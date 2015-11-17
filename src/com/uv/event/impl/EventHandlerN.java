package com.uv.event.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/12.
 */
public abstract class EventHandlerN extends EventHandlerS {
    private static Log log = LogFactory.getLog(EventHandlerN.class);

    private Integer execCount;

    @Override
    public int canExecute() {
        if (execCount == null || execCount == Integer.MAX_VALUE) {
            return 1;
        } else {
            synchronized (this) {
                return --execCount;
            }
        }
    }

//    public EventHandlerN(Integer execCount) {
//        this.execCount = execCount;
//    }


    public EventHandlerN(long id, String desc) {
        super(id, desc);
    }

    public EventHandlerN(long id, Integer execCount) {
        super(id);
        this.execCount = execCount;
    }

    public EventHandlerN(long id) {
        super(id);
    }

    public EventHandlerN(long id, String desc, Integer execCount) {
        super(id, desc);
        this.execCount = execCount;
    }
}