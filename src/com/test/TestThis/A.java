package com.test.TestThis;

/**
 * Created by uv2sun on 15/11/9.
 */
public class A {
    public void test(B b) {
        b.printCall(this);
    }

    @Override
    public String toString() {
        return "A{}";
    }
}
