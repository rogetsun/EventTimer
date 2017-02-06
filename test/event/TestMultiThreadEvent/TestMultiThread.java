package event.TestMultiThreadEvent;

/**
 * Created by uv2sun on 15/11/17.
 */
public class TestMultiThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new InitEvent());
        t.start();
        for (int i = 0; i < 100; i++) {
            new Thread(new TriggerEvent("cmd" + i % 5)).start();
            Thread.sleep(2000);
        }
    }
}
