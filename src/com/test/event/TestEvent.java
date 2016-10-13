package com.test.event;

import com.uv.event.EventEmitter;
import com.uv.event.EventEmitterFactory;
import com.uv.event.impl.executor.EventExecutorImpl;
import com.uv.event.impl.EventHandlerN;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/10.
 */
public class TestEvent {
    private static Log log = LogFactory.getLog(TestEvent.class);

    public static void main(String[] args) throws InterruptedException {

        EventEmitter ee = EventEmitterFactory.getEventEmitter(new EventExecutorImpl());

        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 0) {
                ee.on("say", new EventHandlerN((long) i, 1) {
                    @Override
                    public void deal(String eventName, JSONObject data) {
                        if (data.getBoolean("end")) {
                            log.debug("{" + getEventHandlerID() + "} end");
                        }
                    }
                });
            } else {
                ee.on("say", new EventHandlerN((long) i) {
                    @Override
                    public void deal(String eventName, JSONObject data) {
                        if (data.getBoolean("end")) {
                            log.debug("{" + getEventHandlerID() + "} end");
                        }
                    }
                });
            }
        }
        log.debug("begin");

        int count = 1;
        for (int i = 1; i <= count; i++) {
            JSONObject data = new JSONObject();
            data.put("name", "litx" + i);
            if (i == count)
                data.put("end", true);
            else
                data.put("end", false);
            ee.trigger("say", data);
//            Thread.sleep(2);
        }
    }
}
