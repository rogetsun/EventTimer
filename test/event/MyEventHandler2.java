package event;

import com.uv.event.impl.EventHandlerN;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/12.
 */
public class MyEventHandler2 extends EventHandlerN {
    private static Log log = LogFactory.getLog(MyEventHandler2.class);


    @Override
    public void deal(String eventName, JSONObject data) {
        log.debug(eventName + " trigger me {" + getEventHandlerID() + "}");
        data.accumulate("handler", "handler2 deal");
        log.debug(data);
    }


    public MyEventHandler2(long id, String desc, Integer execCount) {
        super(id, desc, execCount);
    }

    public MyEventHandler2(long id) {
        super(id);
    }

    public MyEventHandler2(long id, Integer execCount) {
        super(id, execCount);

    }
}
