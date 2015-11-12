package com.test.ThreadShareList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by uv2sun on 15/11/10.
 */
public class MyRunnable implements Runnable {

    private static Log log = LogFactory.getLog("");
    //    public static String[] list = new String[100];
    public static ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<String>();

    @Override
    public void run() {
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String s = it.next();
            if (s.equals("remove")) {
                it.remove();
            }
        }
    }
}
