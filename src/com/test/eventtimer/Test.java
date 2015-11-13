package com.test.eventtimer;

import com.uv.event.EventUtil;
import com.uv.event.impl.EventHandlerN;
import com.uv.timer.EventTriggerTask;
import com.uv.timer.TimerUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/13.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        EventUtil.on("litx", new EventHandlerN(1L) {
            private Log log = LogFactory.getLog(Test.class);

            @Override
            public void deal(String eventName, JSONObject data) {
                log.debug(eventName + " trigger " + getEventHandlerID() + "");
            }
        });
        EventUtil.on("songyw", new EventHandlerN(1000L) {
            private Log log = LogFactory.getLog(Test.class);

            @Override
            public void deal(String eventName, JSONObject data) {
                log.debug(eventName + " trigger " + getEventHandlerID() + "");
            }
        });

        TimerUtil.timeout(new EventTriggerTask("songyw"), 1000);
        TimerUtil.interval(new EventTriggerTask("litx"), 1000, 1000);

        Thread.sleep(5000);
        TimerUtil.cancelAll();
    }
}
