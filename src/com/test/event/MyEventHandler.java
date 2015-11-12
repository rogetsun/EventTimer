package com.test.event;

import com.uv.event.impl.EventHandlerS;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/10.
 */
public class MyEventHandler extends EventHandlerS {
    private static Log log = LogFactory.getLog(MyEventHandler.class);


    @Override
    public void deal(String eventName, JSONObject data) {
        log.debug(eventName + " trigger me {" + getEventHandlerID() + "}");
        log.debug(data);
    }

    public MyEventHandler(long id) {
        super(id);
    }

    public MyEventHandler(long id, String desc) {

        super(id, desc);
    }
}
