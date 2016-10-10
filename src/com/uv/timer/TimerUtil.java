package com.uv.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by uv2sun on 15/11/12.
 */
public class TimerUtil {
    private static ScheduledExecutorService scheduledExecutorService;
    private static List<ScheduledFuture> scheduledFutureList;
    private static Log log = LogFactory.getLog(TimerUtil.class);

    static {
        int coreCount = Runtime.getRuntime().availableProcessors();
        log.debug("系统cpu内核数:" + coreCount);
        scheduledExecutorService = Executors.newScheduledThreadPool(coreCount);
        scheduledFutureList = new ArrayList<ScheduledFuture>();
        log.debug("TimerUtil init end");
    }

    /**
     * 延迟delay毫秒后执行task一次
     *
     * @param task
     * @param delay
     * @return
     */
    public static ScheduledFuture timeout(TimerTask task, long delay) {
        return scheduledExecutorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * delay毫秒后,每rate毫秒执行一次task
     *
     * @param task
     * @param rate
     * @return
     */
    public static ScheduledFuture interval(TimerTask task, long delay, long rate) {
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(task, delay, rate, TimeUnit.MILLISECONDS);
        scheduledFutureList.add(scheduledFuture);
        return scheduledFuture;
    }


    /**
     * 取消所有指定频率执行的任务
     */
    public static void cancelAll() {
        log.debug("rate timerTask count=" + scheduledFutureList.size());
        for (Iterator<ScheduledFuture> it = scheduledFutureList.iterator(); it.hasNext(); ) {
            ScheduledFuture scheduledFuture = it.next();
            scheduledFuture.cancel(false);
            log.debug("cancel " + scheduledFuture);
            it.remove();
        }
    }

    public static boolean isShutdown() {
        return scheduledExecutorService.isShutdown();
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
