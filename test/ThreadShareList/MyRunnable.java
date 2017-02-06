package ThreadShareList;

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
    /**
     * 非阻塞线程安全的list.可用于不同现成之间数据共享.
     * 支持一个线程添加元素,另一个线程移除元素
     */
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
