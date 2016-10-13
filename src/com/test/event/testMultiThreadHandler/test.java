package com.test.event.testMultiThreadHandler;

import com.uv.event.EventHandler;
import com.uv.event.EventUtil;
import com.uv.event.impl.EventHandlerN;
import com.uv.event.impl.EventHandlerS;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 16/9/28.
 */
public class test {
    public static void main(String[] args) {
        EventUtil.init(5);
//        EventUtil.on("eh", new MyHandler());
        EventUtil.on("eh", MyHandler.class);
        for (int i = 0; i < 10; i++) {
            EventUtil.trigger("eh", JSONObject.fromObject("{id:" + i + "}"));
        }
    }


}
