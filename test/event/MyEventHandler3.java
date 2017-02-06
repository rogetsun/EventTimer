package event;

import com.uv.event.impl.EventHandlerS;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 2017/2/6.
 */
public class MyEventHandler3 extends EventHandlerS {
    private static Log log = LogFactory.getLog(MyEventHandler.class);

    @Override
    public void deal(String eventName, JSONObject data) {
        log.debug(eventName + " trigger me MyEventHandler3{" + getEventHandlerID() + "}");
        data.accumulate("handler", "handler deal");
        log.debug(data);
    }
}
