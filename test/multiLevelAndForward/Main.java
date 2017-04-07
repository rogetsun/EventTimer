package multiLevelAndForward;

import com.uv.event.EventHandler;
import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 2017/4/7.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        EventHandler ehA = new AEventHandler();
        EventHandler ehAA = new AAEventHandler();
        EventHandler ehB = new BEventHandler();
        EventUtil.on("A", ehA);
        EventUtil.on("A" + EventUtil.EVENT_SEP + "A", ehAA);
        EventUtil.on("B", ehB);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            if (i % 5 == 2) {
                EventUtil.forwardOnce("A@A", (eventName, data) -> {
                    System.out.println("forward:" + eventName + ", data:" + data);
                    return "B";
                });
            }
            if (i == 8) {
                EventUtil.remove("A@A");
            }
            EventUtil.trigger("A@A", JSONObject.fromObject("{idx:" + i + "}"));
        }
    }
}
