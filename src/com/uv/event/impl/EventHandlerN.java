package com.uv.event.impl;

import net.sf.json.JSONObject;
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

    @Override
    public void afterExec(String eventName, JSONObject data) {
    }

    @Override
    public boolean beforeExec(String eventName, JSONObject data) {
        return true;
    }

//    public EventHandlerN(Integer execCount) {
//        this.execCount = execCount;
//    }


    public Integer getExecCount() {
        return execCount;
    }

    public void setExecCount(Integer execCount) {
        this.execCount = execCount;
    }

    public EventHandlerN(long id, String desc) {
        super(id, desc);
    }

    public EventHandlerN(long id, Integer execCount) {
        super(id);
        this.execCount = execCount;
    }

    public EventHandlerN() {
    }

    public EventHandlerN(long id) {
        super(id);
    }

    public EventHandlerN(long id, String desc, Integer execCount) {
        super(id, desc);
        this.execCount = execCount;
    }
}
