package testEventSplit;

import com.uv.event.EventHandler;
import com.uv.event.EventUtil;
import com.uv.event.EventWatcher;
import event.MyEventHandler;
import event.MyEventHandler3;
import net.sf.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        EventUtil.init(4, 16);
        EventHandler eh1 = new MyEventHandler(1, "fdsa");
        EventHandler eh3 = new MyEventHandler3();
        EventUtil.on("eh1", eh1);
        EventUtil.on("eh3", eh3);
        EventUtil.setEventSpliter("eh3", data -> {
            if (data.has("id")) {
                int id = data.getInt("id");
                return id % 10;
            } else {
                return 0;
            }
        });

        EventWatcher ew = new EventWatcher();
        new Thread(ew, "WatcherThread").start();
        new Thread(() -> {
            int i = 100;
            while (i-- > 0) {
                int c = Double.valueOf(Math.random() * 100).intValue();
//                EventUtil.trigger("eh1", JSONObject.fromObject("{id:" + c + ", name:'abc'}"));
                EventUtil.trigger("eh3", JSONObject.fromObject("{id:" + c + ", name:'abc'}"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "TriggerThread").start();
    }
}
