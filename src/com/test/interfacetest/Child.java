package com.test.interfacetest;

import net.sf.json.JSONObject;

/**
 * Created by uv2sun on 15/11/6.
 */
public class Child implements Parent {

    public JSONObject getMsg() {
        return this._msg.getJSONObject("msg");
    }

    public void setMsg(JSONObject msg) {
        this._msg.element("msg", msg);
    }

    public Child() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return this._msg.toString();
    }
}
