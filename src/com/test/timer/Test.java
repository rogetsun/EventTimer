package com.test.timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by uv2sun on 15/11/12.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);
        TimerTask tt1 = new MyTimerTask(1);
        TimerTask tt2 = new MyTimerTask(2);
        TimerTask tt3 = new MyTimerTask(3);
        TimerTask tt4 = new MyTimerTask(4);

        ScheduledFuture sf1 = ses.scheduleAtFixedRate(tt1, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture sf2 = ses.scheduleAtFixedRate(tt2, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture sf3 = ses.scheduleAtFixedRate(tt3, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture sf4 = ses.scheduleAtFixedRate(tt4, 1, 1, TimeUnit.SECONDS);

        Thread.sleep(10000);
        sf1.cancel(false);
        sf2.cancel(false);
        sf3.cancel(false);
        sf4.cancel(false);

//        ses.scheduleAtFixedRate(new MyTimerTask(5), 1, 1, TimeUnit.SECONDS);

    }
}
