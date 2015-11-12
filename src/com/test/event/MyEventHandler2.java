package com.test.event;

import com.uv.event.impl.EventHandlerN;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/12.
 */
public class MyEventHandler2 extends EventHandlerN {
    private static Log log = LogFactory.getLog(MyEventHandler2.class);

    @Override
    public void deal(JSONObject data) {
        log.debug(data);
    }

    public MyEventHandler2(Integer execCount) {
        super(execCount);
    }

    public MyEventHandler2() {
    }
}
