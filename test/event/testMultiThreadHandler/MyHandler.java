package event.testMultiThreadHandler;

import com.uv.event.impl.EventHandlerS;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by uv2sun on 16/9/28.
 */
public class MyHandler extends EventHandlerS {
    private Object o = new Object();

    @Override
    public void deal(String eventName, JSONObject data) {
        System.out.println(data.get("id") + "," + new Date().toString());
        System.out.println(o);
        System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(data.get("id") + " end," + new Date().toString());
    }
}
