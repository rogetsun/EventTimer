package multiLevelAndForward;

import com.uv.event.impl.EventHandlerS;
import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 2017/4/7.
 */
public class BEventHandler extends EventHandlerS {
    @Override
    public void deal(String eventName, JSONObject data) {
        System.out.println(this.getClass().getName() + ":" + data);
    }
}
