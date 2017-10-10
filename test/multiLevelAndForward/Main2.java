package multiLevelAndForward;

import com.uv.event.EventHandler;
import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        EventHandler a = new AEventHandler();
        EventHandler b = new BEventHandler();
        EventHandler aa = new AAEventHandler();

        EventUtil.on("A", a);
        EventUtil.on("A@A", aa);
        EventUtil.on("A|B", b);

        System.out.println("---------------------------");
        EventUtil.trigger("A|B", JSONObject.fromObject("{A|B:'ab'}"));

        Thread.sleep(1000);

    }

}
