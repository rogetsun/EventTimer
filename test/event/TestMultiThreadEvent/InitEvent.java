package event.TestMultiThreadEvent;

import com.uv.event.EventHandler;
import com.uv.event.EventUtil;
import com.uv.event.impl.EventHandlerN;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/17.
 */
public class InitEvent implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            EventHandler eh = new EventHandlerN((long) i) {
                @Override
                public void deal(String eventName, JSONObject data) {
                    System.out.println(eventName + " trigger me with " + data);
                }
            };
            EventUtil.on("cmd" + i, eh);
        }
    }
}
