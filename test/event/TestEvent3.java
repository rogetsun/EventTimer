package event;

import com.uv.event.EventUtil;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by uv2sun on 15/11/12.
 */
public class TestEvent3 {
    public static void main(String[] args) {
        EventUtil.on("say", new MyEventHandler2(new Date().getTime()));
        EventUtil.on("say", new MyEventHandler2(1L, 2));
        EventUtil.trigger("say", JSONObject.fromObject("{name:'litx'}"));
        EventUtil.trigger("say", JSONObject.fromObject("{name:'songyw'}"));
        EventUtil.trigger("say", JSONObject.fromObject("{name:'xxxxx'}"));

    }
}
