package timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.TimerTask;

/**
 * Created by uv2sun on 15/11/12.
 */
public class MyTimerTask extends TimerTask {
    Log log = LogFactory.getLog("");

    @Override
    public void run() {
        log.debug("[" + this.id + "] i am running");
    }

    public MyTimerTask(int id) {
        this.id = id;
    }

    private int id;
}
