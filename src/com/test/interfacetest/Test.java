package com.test.interfacetest;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/6.
 */
public class Test {
    public static void main(String[] args) {
        Child c1 = new Child();
        c1.setMsg(JSONObject.fromObject("{name:'litx'}"));
        Child c2 = new Child();
        c2.setMsg(JSONObject.fromObject("{name1:'songyw'}"));

        System.out.println(c1);
        System.out.println(c2);
    }
}
