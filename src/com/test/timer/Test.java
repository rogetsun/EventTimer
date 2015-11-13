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

        ScheduledFuture sf1 = ses.scheduleAtFixedRate(tt1, 2, 2, TimeUnit.SECONDS);
        ScheduledFuture sf2 = ses.schedule(tt2, 1, TimeUnit.SECONDS);
//        sf2.cancel(true);
        Thread.sleep(2000);
//        sf1.cancel(false);
//        sf2.cancel(false);

//        ses.scheduleAtFixedRate(new MyTimerTask(5), 1, 1, TimeUnit.SECONDS);

    }
}
