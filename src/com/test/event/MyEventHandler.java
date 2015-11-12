package com.test.event;

import com.uv.event.impl.EventHandlerAbs;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/10.
 */
public class MyEventHandler extends EventHandlerAbs {
    private static Log log = LogFactory.getLog(MyEventHandler.class);

    @Override
    public void deal(JSONObject data) {
        log.debug("begin deal");
        log.debug(data);
        log.debug("end deal");
    }

    public MyEventHandler(JSONObject properties) {
        super(properties);
    }
}
