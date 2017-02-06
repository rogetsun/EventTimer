package TestThis;

/**
 * Created by uv2sun on 15/11/9.
 */
public class TestThis {
    public static void main(String[] args) {
        B b = new B();
        A a = new A();
        a.test(b);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
