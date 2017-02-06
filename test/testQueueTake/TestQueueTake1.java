package testQueueTake;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * Created by uv2sun on 16/10/12.
 */
public class TestQueueTake1 {
    private static TransferQueue<String> queue = new LinkedTransferQueue<>();

    public static void main(String[] args) throws InterruptedException {

        int c = 10;
        for (int i = 0; i < c; i++) {
            queue.offer("queue " + i);
        }
        for (int i = 0; i < c + 2; i++) {
//            System.out.println(queue.take());
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
        }
    }
}
