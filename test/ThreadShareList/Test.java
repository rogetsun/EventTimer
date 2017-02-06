package ThreadShareList;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by uv2sun on 15/11/10.
 */
public class Test {
    private static Log log = LogFactory.getLog("");

    public static void main(String[] args) throws InterruptedException {

        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("remove");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
        MyRunnable.list.add("1");
//        for (int i = 0; i < 1000000; i++) {
//            String tmp = i % 100 == 0 ? "remove" : i + "";
//            MyRunnable.list.add(tmp);
//        }

        MyRunnable mr1 = new MyRunnable();
        MyRunnable mr2 = new MyRunnable();
        MyRunnable mr3 = new MyRunnable();
        log.debug("begin");
        new Thread(mr2).start();
        new Thread(mr1).start();
        new Thread(mr3).start();
        log.debug("end");
    }
}
